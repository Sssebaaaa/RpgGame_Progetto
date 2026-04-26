public class Player extends Character {
    private final String role;
    private boolean abilityUsed;
    private boolean doubleAttackReady;
    private int movementHeal;
    private int bonusAttackDamage;

    public Player(String name, String role, String description, int health, int strength, int attack, int defense) {
        super(name, description, health, strength, attack, defense, 0);
        this.role = role;
        this.abilityUsed = false;
        this.doubleAttackReady = false;
        this.movementHeal = 0;
        this.bonusAttackDamage = 0;
    }

    public String getRole() {
        return role;
    }

    public void resetCombatState() {
        setDefending(false);
        abilityUsed = false;
        doubleAttackReady = false;
        getAttack().setCurrent(getAttack().getMax());
        bonusAttackDamage = 0;
    }

    public boolean specialAbility() {
        if (abilityUsed) {
            return false;
        }
        abilityUsed = true;
        if (getName().equalsIgnoreCase("Unit 734")) {
            bonusAttackDamage = Math.max(4, getAttack().getMax() / 2);
            getHealth().subtract(10);
            return true;
        }
        if (getName().equalsIgnoreCase("Circuit")) {
            doubleAttackReady = true;
            return true;
        }
        if (getName().equalsIgnoreCase("Echo")) {
            movementHeal = 8;
            return true;
        }
        return false;
    }

    public boolean isDoubleAttackReady() {
        return doubleAttackReady;
    }

    public void consumeDoubleAttack() {
        doubleAttackReady = false;
    }

    public void onMove() {
        if (movementHeal > 0) {
            getHealth().add(movementHeal);
        }
    }

    public int consumeBonusAttackDamage() {
        int damage = bonusAttackDamage;
        bonusAttackDamage = 0;
        return damage;
    }
}
