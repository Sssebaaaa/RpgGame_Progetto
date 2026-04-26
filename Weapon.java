public class Weapon extends Item {
    private int damage;
    private int durability;
    private final int maxDurability;
    private final int requiredStrength;

    public Weapon(String name, String description, int weight, int damage, int durability, int requiredStrength) {
        super(name, description, weight, true, true);
        this.damage = Math.max(0, damage);
        this.durability = Math.max(1, durability);
        this.maxDurability = this.durability;
        this.requiredStrength = Math.max(0, requiredStrength);
    }

    public int getDamage() {
        return damage;
    }

    public int getDurability() {
        return durability;
    }

    public int getRequiredStrength() {
        return requiredStrength;
    }

    public void useOnce() {
        durability = Math.max(0, durability - 1);
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    public void boostPower() {
        damage = Math.max(1, (int) Math.ceil(damage * 1.3));
        durability = maxDurability;
    }

    @Override
    public String getShortDescription() {
        return getName() + " - damage " + damage + ", durability " + durability + ", required strength " + requiredStrength;
    }
}
