public class Circuit extends Player {
    private boolean abilityUsed = false;

    public Circuit() {
        super("CIRCUIT", 100, 35, 10, 10);
    }

    public void specialAbility() {
        if (!abilityUsed) {
            abilityUsed = true;
            System.out.println("[SWIFT STRIKE] Speed doubled! Double attack next turn!");
            // Note: Combat in Main.java should handle double attacks if active.
        } else {
            System.out.println("Already activated in this combat.");
        }
    }

    public boolean isAbilityActive() { return abilityUsed; }
    public void resetAbility() { abilityUsed = false; }
}