public class Echo extends Player {
    private boolean abilityActive;

    public Echo() {
        super("ECHO", 120, 20, 15, 12);
        this.abilityActive = false;
    }

    public void specialAbility() {
        if (abilityActive) {
            System.out.println("\n[AUTO-REPAIR] is already active!");
            return;
        }

        abilityActive = true;
        System.out.println("\n[AUTO-REPAIR] ACTIVATED!");
        System.out.println("You will regenerate HP while moving!");
    }

    public void onMove() {
        if (abilityActive) {
            int healAmount = 10;
            health.add(healAmount);
            System.out.println("\n[AUTO-REPAIR] Healed for " + healAmount + " HP!");
            System.out.println("Current HP: " + health.getCurrent() + "/" + health.getMax());
        }
    }

    public boolean isAbilityActive() {
        return abilityActive;
    }

    public void deactivateAbility() {
        abilityActive = false;
    }
}