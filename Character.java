import java.util.Random;

public abstract class Character extends Entity {
    protected Stat health, attack, defense;
    protected Weapon equippedWeapon;
    protected Shield equippedShield;
    protected boolean isDefending;
    protected Inventory inventory;
    protected String name;

    public Character(String name, int maxHealth, int attack, int defense, int inventoryCapacity) {
        super(name, "");
        this.name = name;
        this.health = new Stat(maxHealth, maxHealth);
        this.attack = new Stat(attack, attack);
        this.defense = new Stat(defense, defense);
        this.inventory = new Inventory(inventoryCapacity);
    }

    public Stat getHealth() { return health; }
    public Stat getAttack() { return attack; }
    public Stat getDefense() { return defense; }
    public String getName() { return name; }
    public void setIsDefending(boolean def) { this.isDefending = def; }

    public void attack(Character target) {
        int damage = attack.getCurrent() + new Random().nextInt(5);
        if (equippedWeapon != null) {
            damage += equippedWeapon.getDamage();
            equippedWeapon.setDurability(equippedWeapon.getDurability() - 1);
            if (equippedWeapon.getDurability() <= 0) {
                System.out.println(name + "'s weapon broke!");
                equippedWeapon = null;
            }
        }

        int targetDef = target.defense.getCurrent();
        if (target.isDefending) targetDef *= 2;
        if (target.equippedShield != null) {
            targetDef += target.equippedShield.getDefense();
            target.equippedShield.setDurability(target.equippedShield.getDurability() - 1);
            if (target.equippedShield.getDurability() <= 0) {
                System.out.println(target.name + "'s shield shattered!");
                target.equippedShield = null;
            }
        }

        int finalDamage = Math.max(damage - targetDef, 0);
        target.health.subtract(finalDamage);
        System.out.println(name + " deals " + finalDamage + " damage to " + target.getName() + "!");
    }

    public void defend() {
        isDefending = true;
        System.out.println(name + " is defending!");
    }

    public void flee() {
        if (new Random().nextInt(100) < 50) System.out.println("Flee successful!");
        else { System.out.println("Flee failed!"); health.subtract(10); }
    }

    public void showStats() {
        System.out.println("\n--- " + name + " ---");
        System.out.println("HP: " + health.getCurrent() + "/" + health.getMax());
        System.out.println("ATK/DEF: " + attack.getCurrent() + "/" + defense.getCurrent());
        if (equippedWeapon != null) System.out.println("Weapon: " + equippedWeapon);
        if (equippedShield != null) System.out.println("Shield: " + equippedShield);
        System.out.println("Inventory: " + inventory.size());
    }
}