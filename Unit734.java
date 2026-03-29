public class Unit734 extends Player {
    private boolean abilityUsed = false;

    public Unit734() {
        super("UNIT 734", 150, 25, 20, 8);
    }

    public void specialAbility() {
        if (!abilityUsed) {
            int hpCost = health.getCurrent() / 4;
            health.subtract(hpCost);
            attack.add(attack.getMax()); // Temporarily double attack
            abilityUsed = true;
            System.out.println("[OVERCLOCK] Attack doubled! HP Cost: " + hpCost);
        } else {
            System.out.println("Already used in this combat.");
        }
    }

    public void resetAbility() {
        if (abilityUsed) {
            attack.setCurrent(attack.getMax());
            abilityUsed = false;
        }
    }
}