import java.util.Random;

public class Enemy extends Character {
    private int rewardXP;

    public Enemy(String name, int maxHealth, int attack, int defense, int rewardXP) {
        super(name, maxHealth, attack, defense, 0);
        this.rewardXP = rewardXP;
    }

    public int getRewardXP() {
        return rewardXP;
    }

    public void takeTurn(Character target) {
        if (health.getCurrent() < health.getMax() * 0.3) {
            Random rand = new Random();
            if (rand.nextInt(100) < 30) {
                System.out.println(name + " attempts to flee!");
                flee();
                return;
            }
        }

        if (health.getCurrent() < health.getMax() * 0.5 && !isDefending) {
            Random rand = new Random();
            if (rand.nextInt(100) < 40) {
                defend();
                return;
            }
        }

        attack(target);
    }
}