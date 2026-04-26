import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Character extends Entity {
    private final Stat health;
    private final Stat strength;
    private final Stat attack;
    private final Stat defense;
    private final Stat experience;
    private final Inventory inventory;
    private Weapon equippedWeapon;
    private Shield equippedShield;
    private Location location;
    private boolean defending;
    private final Random random;

    public Character(String name, String description, int health, int strength, int attack, int defense, int experience) {
        super(name, description);
        this.health = new Stat(health, health);
        this.strength = new Stat(strength, strength);
        this.attack = new Stat(attack, attack);
        this.defense = new Stat(defense, defense);
        this.experience = new Stat(experience, 50);
        this.inventory = new Inventory();
        this.random = new Random();
    }

    public Stat getHealth() {
        return health;
    }

    public Stat getStrength() {
        return strength;
    }

    public Stat getAttack() {
        return attack;
    }

    public Stat getDefense() {
        return defense;
    }

    public Stat getExperience() {
        return experience;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Shield getEquippedShield() {
        return equippedShield;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setDefending(boolean defending) {
        this.defending = defending;
    }

    public boolean isDefending() {
        return defending;
    }

    public int getCarryCapacity() {
        return strength.getMax();
    }

    public boolean addItem(Item item) {
        if (!item.isTransportable()) {
            return false;
        }
        return inventory.addItem(item, getCarryCapacity());
    }

    public Item removeItem(String itemName) {
        Item item = inventory.removeItem(itemName);
        if (item != null && item == equippedWeapon) {
            equippedWeapon = null;
        }
        if (item != null && item == equippedShield) {
            equippedShield = null;
        }
        return item;
    }

    public Item findItem(String itemName) {
        return inventory.findItem(itemName);
    }

    public boolean equip(String itemName) {
        Item item = findItem(itemName);
        if (item instanceof Weapon weapon) {
            if (strength.getCurrent() < weapon.getRequiredStrength()) {
                return false;
            }
            equippedWeapon = weapon;
            return true;
        }
        if (item instanceof Shield shield) {
            equippedShield = shield;
            return true;
        }
        return false;
    }

    public int attackTarget(Character target) {
        int weaponDamage = equippedWeapon == null ? 0 : equippedWeapon.getDamage();
        int baseDamage = attack.getCurrent() + weaponDamage + random.nextInt(4);
        int targetDefense = target.defense.getCurrent();
        if (target.defending) {
            targetDefense += Math.max(1, target.defense.getCurrent() / 2);
        }
        if (target.equippedShield != null) {
            targetDefense += target.equippedShield.getDefense();
            target.equippedShield.useOnce();
            if (target.equippedShield.isBroken()) {
                target.removeBrokenShield();
            }
        }
        if (equippedWeapon != null) {
            equippedWeapon.useOnce();
            if (equippedWeapon.isBroken()) {
                removeBrokenWeapon();
            }
        }
        int finalDamage = Math.max(1, baseDamage - targetDefense);
        target.health.subtract(finalDamage);
        return finalDamage;
    }

    private void removeBrokenWeapon() {
        if (equippedWeapon != null) {
            inventory.getItems().remove(equippedWeapon);
            equippedWeapon = null;
        }
    }

    private void removeBrokenShield() {
        if (equippedShield != null) {
            inventory.getItems().remove(equippedShield);
            equippedShield = null;
        }
    }

    public void useEquippedWeaponOutsideCombat() {
        if (equippedWeapon == null) {
            return;
        }
        equippedWeapon.useOnce();
        if (equippedWeapon.isBroken()) {
            removeBrokenWeapon();
        }
    }

    public void addExperience(int amount) {
        if (amount <= 0) {
            return;
        }
        experience.add(amount);
        while (experience.getCurrent() >= experience.getMax()) {
            experience.subtract(experience.getMax());
            levelUp();
        }
    }

    private void levelUp() {
        increaseStatByTwentyPercent(health);
        increaseStatByTwentyPercent(strength);
        increaseStatByTwentyPercent(attack);
        increaseStatByTwentyPercent(defense);
        health.restore();
        strength.restore();
        attack.restore();
        defense.restore();
    }

    private void increaseStatByTwentyPercent(Stat stat) {
        int newMax = Math.max(stat.getMax() + 1, (int) Math.ceil(stat.getMax() * 1.2));
        stat.setMax(newMax);
    }

    public List<Item> dropAllItems() {
        List<Item> dropped = new ArrayList<>(inventory.getItems());
        inventory.getItems().clear();
        equippedWeapon = null;
        equippedShield = null;
        return dropped;
    }

    public String statsBlock() {
        return getName()
                + System.lineSeparator() + "Health " + health
                + System.lineSeparator() + "Strength " + strength
                + System.lineSeparator() + "Attack " + attack
                + System.lineSeparator() + "Defense " + defense
                + System.lineSeparator() + "Experience " + experience;
    }
}
