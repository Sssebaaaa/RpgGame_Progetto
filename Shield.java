public class Shield extends Item {
    private int defense;
    private int durability;
    private final int maxDurability;

    public Shield(String name, String description, int weight, int defense, int durability) {
        super(name, description, weight, true, true);
        this.defense = Math.max(0, defense);
        this.durability = Math.max(1, durability);
        this.maxDurability = this.durability;
    }

    public int getDefense() {
        return defense;
    }

    public int getDurability() {
        return durability;
    }

    public void useOnce() {
        durability = Math.max(0, durability - 1);
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    public void boostPower() {
        defense = Math.max(1, (int) Math.ceil(defense * 1.3));
        durability = maxDurability;
    }

    @Override
    public String getShortDescription() {
        return getName() + " - defense " + defense + ", durability " + durability;
    }
}
