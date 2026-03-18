public abstract class Character {
    private Stat health;
    private Stat strength;
    private Stat attack;
    private Stat defense;
    private int experience;
    private Inventory inventory;

    public void attack(Character target) {
        // da mettere per attaccare
    }

    public void defend() {
        // per difendere
    }

    public void use(Potion p) {
        // usare pozione
    }

    public void flee() {
        // fuggire
    }
}