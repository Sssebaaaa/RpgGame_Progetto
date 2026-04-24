import java.util.Scanner;

public class Main {
    private static World world;
    private static Player player;
    private static Enemy currentEnemy;
    private static boolean inCombat = false;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("NEO-VERIDIA: NEON SHADOWS...");
        sceltaPersonaggio();
        gameLoop();
    }

    static void sceltaPersonaggio() {
        System.out.println("[1] UNIT 734 (TANK)\n[2] CIRCUIT (SCOUT)\n[3] ECHO (SUPPORT)\nChoice: ");
        int val = (new Scanner(System.in)).nextInt();
        switch (val) {
            case 1 -> player = new Unit734();
            case 2 -> player = new Circuit();
            case 3 -> player = new Echo();
            default -> player = new Unit734();
        }
        world = new World();
        player.setWorld(world);
        player.setLocation(world.getStartingLocation());
        world.lookAround(player.getLocation());
    }

    static void gameLoop() {
        while (true) {
            if (player.getHealth().isEmpty()) { gameOver(false); return; }
            checkForEnemies();
            if (inCombat) combatLoop(); else explorationLoop();
        }
    }

    static void explorationLoop() {
        System.out.println("\n(Type 'help' for a list of commands)");
        System.out.print("> ");
        String[] parts = scanner.nextLine().toLowerCase().split(" ", 2);
        String cmd = parts[0];
        String arg = parts.length > 1 ? parts[1] : "";

        switch (cmd) {
            case "look" -> world.lookAround(player.getLocation());
            case "status" -> player.showStats();
            case "north", "south", "east", "west" -> {
                Direction dir = Direction.fromString(cmd);
                if (dir != null) {
                    player.move(dir);
                    if (player instanceof Echo) ((Echo) player).onMove();
                    checkForLevelUp();
                }
            }
            case "pick" -> player.pickUp(arg);
            case "drop" -> player.drop(arg);
            case "use" -> player.usePotion(arg);
            case "equip" -> {
                if (arg.contains("weapon")) player.equipWeapon(arg);
                else player.equipShield(arg);
            }
            case "open" -> openContainer(arg);
            case "help" -> System.out.println("Commands: LOOK, STATUS, NORTH/SOUTH/EAST/WEST, PICK, DROP, USE, EQUIP, OPEN, QUIT");
            case "quit", "exit" -> System.exit(0);
            default -> System.out.println("Command not recognized.");
        }
    }

    static void openContainer(String itemName) {
        for (Item i : player.getLocation().getItems()) {
            if (i instanceof Container && i.toString().toLowerCase().contains(itemName)) {
                ((Container) i).open();
                System.out.println("Content: " + ((Container) i).getContent());
                return;
            }
        }
        System.out.println("No container found with that name.");
    }

    static void checkForEnemies() {
        if (!player.getLocation().getCharacters().isEmpty()) {
            startCombat((Enemy) player.getLocation().getCharacters().get(0));
        }
    }

    static void startCombat(Enemy enemy) {
        currentEnemy = enemy;
        inCombat = true;
        System.out.println("\nENGAGING COMBAT WITH: " + enemy.getName());
    }

    static void combatLoop() {
        while (inCombat) {
            if (player.getHealth().isEmpty()) { gameOver(false); return; }
            if (currentEnemy.getHealth().isEmpty()) { victory(); return; }

            System.out.print("HP " + player.getHealth().getCurrent() + " vs " + currentEnemy.getName() + " HP " + currentEnemy.getHealth().getCurrent());
            System.out.println("\n(Type 'help' for a list of commands)");
            System.out.print("(attack/defend/ability/use/flee): ");
            String action = scanner.nextLine().toLowerCase();

            player.setIsDefending(false);
            boolean fled = false;
            switch (action) {
                case "attack" -> player.attack(currentEnemy);
                case "defend" -> player.defend();
                case "ability" -> player.specialAbility();
                case "use" -> { System.out.print("What? "); player.usePotion(scanner.nextLine()); }
                case "flee" -> fled = player.flee();
                default -> System.out.println("You skip your turn.");
            }

            if (fled) {
                // Muovi il giocatore in una stanza adiacente per evitare combattimento immediato
                Location current = player.getLocation();
                if (!current.getExits().isEmpty()) {
                    Location dest = current.getExits().values().iterator().next();
                    player.setLocation(dest);
                    System.out.println("You escape to another location.");
                    System.out.println(dest.getDescription());
                }
                inCombat = false;
                return;
            }

            if (currentEnemy.getHealth().getCurrent() > 0) {
                System.out.println("\nEnemy turn...");
                currentEnemy.setIsDefending(false);
                currentEnemy.takeTurn(player);
            }
        }
    }

    static void victory() {
        System.out.println("VICTORY! XP: " + currentEnemy.getRewardXP());
        player.addExperience(currentEnemy.getRewardXP());
        player.getLocation().getCharacters().remove(currentEnemy);
        inCombat = false;
        checkForLevelUp();
        if (player.getLocation().getDescription().contains("EXIT")) checkEndGame();
    }

    static void checkForLevelUp() {
        if (player.getExperience() >= 50) {
            System.out.println("LEVEL UP! +10 Max HP, +5 Attack");
            player.getHealth().setMax(player.getHealth().getMax() + 10);
            player.getHealth().setCurrent(player.getHealth().getMax());
            player.getAttack().setMax(player.getAttack().getMax() + 5);
            player.getAttack().setCurrent(player.getAttack().getMax());
            player.addExperience(-50);
        }
    }

    static void checkEndGame() {
        boolean enemiesLeft = false;
        for (Location loc : world.getLocations().values()) {
            if (!loc.getCharacters().isEmpty()) { enemiesLeft = true; break; }
        }
        if (!enemiesLeft) gameOver(true);
    }

    static void gameOver(boolean win) {
        System.out.println(win ? "CONGRATULATIONS! YOU WIN!" : "GAME OVER...");
        System.exit(0);
    }
}