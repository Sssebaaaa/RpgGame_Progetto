import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    private static World world;
    private static Player player;
    private static Enemy currentEnemy;
    private static boolean inCombat;

    private static String readInput() {
        try {
            return SCANNER.nextLine();
        } catch (Exception exception) {
            return null;
        }
    }

    public static void main(String[] args) {
        printIntro();
        chooseCharacter();
        gameLoop();
    }

    private static void printIntro() {
        System.out.println("NEO-VERIDIA: NEON SHADOWS");
        System.out.println("Omni-Corp stole the Source Code that can free sentient machines.");
        System.out.println("Enter the city core, survive the guards, recover the code and escape.");
        System.out.println();
    }

    private static void chooseCharacter() {
        System.out.println("Choose your unit:");
        System.out.println("1. Unit 734 - Tank");
        System.out.println("2. Circuit - Scout");
        System.out.println("3. Echo - Support");
        System.out.print("> ");
        String input = readInput();
        if (input == null) {
            System.out.println("Input stream closed.");
            System.exit(0);
        }
        input = input.trim();
        if ("2".equals(input)) {
            player = new Circuit();
        } else if ("3".equals(input)) {
            player = new Echo();
        } else {
            player = new Unit734();
        }

        world = new World();
        player.setLocation(world.getStartingLocation());
        System.out.println();
        System.out.println("You are " + player.getName() + ", the " + player.getRole() + ".");
        lookAround();
        System.out.println("Type 'help' to see the available commands.");
    }

    private static void gameLoop() {
        while (true) {
            if (player.getHealth().isEmpty()) {
                System.out.println("You collapse. Game over.");
                return;
            }

            if (hasWon()) {
                System.out.println("You reach the elevator with the Source Code.");
                System.out.println("Neo-Veridia will never be the same again. You win.");
                return;
            }

            if (inCombat) {
                runCombatTurn();
            } else {
                runExplorationTurn();
            }
        }
    }

    private static void runExplorationTurn() {
        System.out.print("\n> ");
        String line = readInput();
        if (line == null) {
            System.out.println("Input stream closed.");
            System.exit(0);
        }
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }

        String[] parts = line.split(" ", 2);
        String command = parts[0].toLowerCase();
        String argument = parts.length > 1 ? parts[1].trim() : "";

        switch (command) {
            case "help" -> printExplorationHelp();
            case "look" -> lookAround();
            case "status" -> System.out.println(player.statsBlock());
            case "inventory" -> showInventory();
            case "north", "south", "east", "west" -> move(Direction.fromString(command));
            case "go" -> move(Direction.fromString(argument));
            case "take", "pick" -> takeItem(argument);
            case "drop" -> dropItem(argument);
            case "equip" -> equipItem(argument);
            case "use" -> {
                if (!usePotion(player, argument)) {
                    System.out.println("You cannot use that.");
                }
            }
            case "open" -> openContainer(argument);
            case "close" -> closeContainer(argument);
            case "unlock" -> unlockContainer(argument);
            case "search" -> searchContainer(argument);
            case "break", "destroy" -> destroyContainer(argument);
            case "talk" -> talkToCharacter(argument);
            case "fight" -> startCombat(argument);
            case "quit", "exit" -> {
                System.out.println("Connection closed.");
                System.exit(0);
            }
            default -> System.out.println("Unknown command.");
        }
    }

    private static void printExplorationHelp() {
        System.out.println("Commands:");
        System.out.println("look, status, inventory");
        System.out.println("north, south, east, west, go <direction>");
        System.out.println("take <item>, drop <item>, equip <item>, use <potion>");
        System.out.println("open <container>, close <container>, unlock <container>");
        System.out.println("search <container>, destroy <container>");
        System.out.println("talk <character>, fight <character>, quit");
    }

    private static void move(Direction direction) {
        if (direction == null) {
            System.out.println("That direction does not exist.");
            return;
        }
        Location next = world.getExit(player.getLocation(), direction);
        if (next == null) {
            System.out.println("You cannot go that way.");
            return;
        }
        player.setLocation(next);
        player.onMove();
        lookAround();
    }

    private static void lookAround() {
        Location location = player.getLocation();
        System.out.println();
        System.out.println(location.describe());
        printItems(location);
        printCharacters(location);
        printExits(location);
    }

    private static void printItems(Location location) {
        if (location.getItems().isEmpty()) {
            System.out.println("Items: none");
            return;
        }
        System.out.println("Items:");
        for (Item item : location.getItems()) {
            System.out.println("- " + item.getShortDescription());
            if (item instanceof Container container && container.isOpen()) {
                if (container.getContents().isEmpty()) {
                    System.out.println("  empty");
                } else {
                    for (Item inside : container.getContents()) {
                        System.out.println("  contains: " + inside.getShortDescription());
                    }
                }
            }
        }
    }

    private static void printCharacters(Location location) {
        if (location.getCharacters().isEmpty()) {
            System.out.println("Characters: none");
            return;
        }
        System.out.println("Characters:");
        for (Character character : location.getCharacters()) {
            System.out.println("- " + character.getName() + " - " + character.getDescription());
            System.out.println("  Health " + character.getHealth());
            System.out.println("  Strength " + character.getStrength());
            System.out.println("  Attack " + character.getAttack());
            System.out.println("  Defense " + character.getDefense());
            System.out.println("  Experience " + character.getExperience());
        }
    }

    private static void printExits(Location location) {
        if (location.getExits().isEmpty()) {
            System.out.println("Exits: none");
            return;
        }
        StringBuilder builder = new StringBuilder("Exits: ");
        boolean first = true;
        for (Direction direction : location.getExits().keySet()) {
            if (!first) {
                builder.append(", ");
            }
            builder.append(direction.getDisplayName());
            first = false;
        }
        System.out.println(builder);
    }

    private static void showInventory() {
        System.out.println("Carry weight: " + player.getInventory().getCurrentWeight() + "/" + player.getCarryCapacity());
        System.out.println(player.getInventory().describe());
        if (player.getEquippedWeapon() != null) {
            System.out.println("Equipped weapon: " + player.getEquippedWeapon().getShortDescription());
        }
        if (player.getEquippedShield() != null) {
            System.out.println("Equipped shield: " + player.getEquippedShield().getShortDescription());
        }
    }

    private static void takeItem(String itemName) {
        if (itemName.isBlank()) {
            System.out.println("Take what?");
            return;
        }
        Item roomItem = findItemInRoom(itemName);
        if (roomItem != null) {
            if (!roomItem.isTransportable()) {
                System.out.println("You cannot carry that.");
                return;
            }
            if (!player.addItem(roomItem)) {
                System.out.println("That is too heavy for your bag.");
                return;
            }
            player.getLocation().getItems().remove(roomItem);
            System.out.println("Taken: " + roomItem.getName());
            return;
        }

        for (Item item : player.getLocation().getItems()) {
            if (item instanceof Container container && container.isOpen()) {
                Item inside = findContainedItem(container, itemName);
                if (inside != null) {
                    if (!player.addItem(inside)) {
                        System.out.println("That is too heavy for your bag.");
                        return;
                    }
                    container.getContents().remove(inside);
                    System.out.println("Taken: " + inside.getName());
                    return;
                }
            }
        }

        System.out.println("No item with that name is available.");
    }

    private static Item findItemInRoom(String itemName) {
        for (Item item : player.getLocation().getItems()) {
            if (matches(item, itemName)) {
                return item;
            }
        }
        return null;
    }

    private static Item findContainedItem(Container container, String itemName) {
        for (Item item : container.getContents()) {
            if (matches(item, itemName)) {
                return item;
            }
        }
        return null;
    }

    private static boolean matches(Item item, String name) {
        return item.getName().equalsIgnoreCase(name) || item.getName().toLowerCase().contains(name.toLowerCase());
    }

    private static void dropItem(String itemName) {
        if (itemName.isBlank()) {
            System.out.println("Drop what?");
            return;
        }
        Item item = player.removeItem(itemName);
        if (item == null) {
            System.out.println("You do not have that item.");
            return;
        }
        player.getLocation().getItems().add(item);
        System.out.println("Dropped: " + item.getName());
    }

    private static void equipItem(String itemName) {
        if (itemName.isBlank()) {
            System.out.println("Equip what?");
            return;
        }
        if (player.equip(itemName)) {
            System.out.println("Equipped: " + itemName);
            return;
        }
        Item item = player.findItem(itemName);
        if (item instanceof Weapon weapon && player.getStrength().getCurrent() < weapon.getRequiredStrength()) {
            System.out.println("You are not strong enough to use that weapon.");
            return;
        }
        System.out.println("That item cannot be equipped.");
    }

    private static void openContainer(String itemName) {
        Container container = findContainer(itemName);
        if (container == null) {
            System.out.println("No container with that name is here.");
            return;
        }
        if (!container.open()) {
            System.out.println("It is locked.");
            return;
        }
        System.out.println(container.getName() + " is now open.");
        searchContainer(itemName);
    }

    private static void closeContainer(String itemName) {
        Container container = findContainer(itemName);
        if (container == null) {
            System.out.println("No container with that name is here.");
            return;
        }
        container.close();
        System.out.println(container.getName() + " is now closed.");
    }

    private static void unlockContainer(String itemName) {
        Container container = findContainer(itemName);
        if (container == null) {
            System.out.println("No container with that name is here.");
            return;
        }
        if (!container.isLocked()) {
            System.out.println("It is already unlocked.");
            return;
        }
        Item key = findMatchingKey(container);
        if (key == null || !container.unlock(key)) {
            System.out.println("You do not have the right key.");
            return;
        }
        System.out.println("Unlocked: " + container.getName());
    }

    private static Item findMatchingKey(Container container) {
        for (Item item : player.getInventory().getItems()) {
            if (container.canUnlockWith(item)) {
                return item;
            }
        }
        return null;
    }

    private static void searchContainer(String itemName) {
        Container container = findContainer(itemName);
        if (container == null) {
            System.out.println("No container with that name is here.");
            return;
        }
        if (!container.isOpen()) {
            System.out.println("It is closed.");
            return;
        }
        if (container.getContents().isEmpty()) {
            System.out.println("It is empty.");
            return;
        }
        System.out.println("Inside " + container.getName() + ":");
        for (Item item : container.getContents()) {
            System.out.println("- " + item.getShortDescription());
        }
    }

    private static void destroyContainer(String itemName) {
        Container container = findContainer(itemName);
        if (container == null) {
            System.out.println("No container with that name is here.");
            return;
        }
        if (!container.isDestructible()) {
            System.out.println("You cannot destroy that.");
            return;
        }
        if (player.getEquippedWeapon() == null) {
            System.out.println("You need a weapon to destroy it.");
            return;
        }
        player.useEquippedWeaponOutsideCombat();
        player.getLocation().getItems().remove(container);
        System.out.println(container.getName() + " is destroyed together with everything inside.");
    }

    private static Container findContainer(String itemName) {
        for (Item item : player.getLocation().getItems()) {
            if (item instanceof Container container) {
                if (container.getName().equalsIgnoreCase(itemName) || container.getName().toLowerCase().contains(itemName.toLowerCase())) {
                    return container;
                }
            }
        }
        return null;
    }

    private static void talkToCharacter(String characterName) {
        Enemy enemy = findEnemy(characterName);
        if (enemy == null) {
            System.out.println("Nobody answers.");
            return;
        }
        System.out.println(enemy.getName() + " says: 'Turn back or be dismantled.'");
    }

    private static void startCombat(String enemyName) {
        Enemy enemy = findEnemy(enemyName);
        if (enemy == null) {
            System.out.println("No enemy with that name is here.");
            return;
        }
        currentEnemy = enemy;
        inCombat = true;
        player.resetCombatState();
        currentEnemy.setDefending(false);
        System.out.println("Combat starts against " + currentEnemy.getName() + ".");
        if (RANDOM.nextBoolean()) {
            System.out.println("You act first.");
        } else {
            System.out.println(currentEnemy.getName() + " acts first.");
            enemyTurn();
        }
    }

    private static Enemy findEnemy(String enemyName) {
        List<Character> characters = player.getLocation().getCharacters();
        if (characters.isEmpty()) {
            return null;
        }
        if (enemyName == null || enemyName.isBlank()) {
            return (Enemy) characters.get(0);
        }
        for (Character character : characters) {
            if (character.getName().equalsIgnoreCase(enemyName) || character.getName().toLowerCase().contains(enemyName.toLowerCase())) {
                return (Enemy) character;
            }
        }
        return null;
    }

    private static void runCombatTurn() {
        if (currentEnemy == null) {
            inCombat = false;
            return;
        }
        if (currentEnemy.getHealth().isEmpty()) {
            handleEnemyDeath();
            return;
        }

        System.out.println();
        System.out.println("Your HP " + player.getHealth() + " | " + currentEnemy.getName() + " HP " + currentEnemy.getHealth());
        System.out.print("(attack, defend, use <potion>, ability, flee, status, help) > ");
        String line = readInput();
        if (line == null) {
            System.out.println("Input stream closed.");
            System.exit(0);
        }
        line = line.trim();
        if (line.isEmpty()) {
            return;
        }

        String[] parts = line.split(" ", 2);
        String command = parts[0].toLowerCase();
        String argument = parts.length > 1 ? parts[1].trim() : "";
        player.setDefending(false);

        switch (command) {
            case "help" -> printCombatHelp();
            case "status" -> System.out.println(player.statsBlock());
            case "defend" -> {
                player.setDefending(true);
                System.out.println("You brace for the next attack.");
                enemyTurn();
            }
            case "attack" -> {
                playerAttack();
                if (inCombat) {
                    enemyTurn();
                }
            }
            case "use" -> {
                if (usePotion(player, argument)) {
                    enemyTurn();
                }
            }
            case "ability" -> {
                if (player.specialAbility()) {
                    System.out.println("Special ability activated.");
                    enemyTurn();
                } else {
                    System.out.println("You cannot use your special ability now.");
                }
            }
            case "flee" -> attemptFlee();
            case "quit", "exit" -> {
                System.out.println("Connection closed.");
                System.exit(0);
            }
            default -> System.out.println("Unknown combat command.");
        }
    }

    private static void printCombatHelp() {
        System.out.println("Combat commands:");
        System.out.println("attack, defend, use <potion>, ability, flee, status");
    }

    private static void playerAttack() {
        int damage = player.attackTarget(currentEnemy);
        int bonusDamage = player.consumeBonusAttackDamage();
        if (bonusDamage > 0 && !currentEnemy.getHealth().isEmpty()) {
            currentEnemy.getHealth().subtract(bonusDamage);
            damage += bonusDamage;
        }
        System.out.println("You deal " + damage + " damage to " + currentEnemy.getName() + ".");
        if (currentEnemy.getHealth().isEmpty()) {
            handleEnemyDeath();
            return;
        }
        if (player.isDoubleAttackReady()) {
            player.consumeDoubleAttack();
            int secondDamage = player.attackTarget(currentEnemy);
            System.out.println("Circuit strikes again for " + secondDamage + " damage.");
            if (currentEnemy.getHealth().isEmpty()) {
                handleEnemyDeath();
            }
        }
    }

    private static void enemyTurn() {
        if (!inCombat || currentEnemy == null || currentEnemy.getHealth().isEmpty()) {
            return;
        }

        currentEnemy.setDefending(false);
        if (currentEnemy.getHealth().getCurrent() <= currentEnemy.getHealth().getMax() / 3) {
            if (usePotion(currentEnemy, "repair")) {
                System.out.println(currentEnemy.getName() + " uses a potion.");
                return;
            }
        }

        if (currentEnemy.findItem("Source Code") == null
                && currentEnemy.getHealth().getCurrent() <= currentEnemy.getHealth().getMax() / 4
                && RANDOM.nextInt(100) < 20) {
            System.out.println(currentEnemy.getName() + " flees into the shadows.");
            moveCharacterToAdjacentLocation(currentEnemy);
            inCombat = false;
            currentEnemy = null;
            return;
        }

        if (RANDOM.nextInt(100) < 25) {
            currentEnemy.setDefending(true);
            System.out.println(currentEnemy.getName() + " takes a defensive stance.");
            return;
        }

        int damage = currentEnemy.attackTarget(player);
        System.out.println(currentEnemy.getName() + " deals " + damage + " damage to you.");
        if (player.getHealth().isEmpty()) {
            List<Item> dropped = player.dropAllItems();
            player.getLocation().getItems().addAll(dropped);
            inCombat = false;
        }
    }

    private static void attemptFlee() {
        if (RANDOM.nextBoolean()) {
            moveCharacterToAdjacentLocation(player);
            System.out.println("You escape the fight.");
            inCombat = false;
            currentEnemy = null;
            lookAround();
            return;
        }
        System.out.println("You fail to escape.");
        enemyTurn();
    }

    private static void handleEnemyDeath() {
        System.out.println(currentEnemy.getName() + " is defeated.");
        int gainedExperience = currentEnemy.getExperience().getCurrent();
        player.addExperience(gainedExperience);
        List<Item> dropped = currentEnemy.dropAllItems();
        player.getLocation().getItems().addAll(dropped);
        player.getLocation().getCharacters().remove(currentEnemy);
        currentEnemy = null;
        inCombat = false;
    }

    private static boolean usePotion(Character character, String itemName) {
        Item item = character.findItem(itemName);
        if (!(item instanceof Potion potion)) {
            return false;
        }

        if ("cure".equals(potion.getEffectType()) || "health".equals(potion.getEffectType()) || "repair".equals(potion.getEffectType())) {
            character.getHealth().add(potion.getPotency());
        } else if ("poison".equals(potion.getEffectType())) {
            character.getHealth().setMax(Math.max(1, character.getHealth().getMax() - potion.getPotency()));
            character.getHealth().subtract(potion.getPotency());
        } else if ("power".equals(potion.getEffectType())) {
            if (character.getEquippedWeapon() != null) {
                character.getEquippedWeapon().boostPower();
            } else if (character.getEquippedShield() != null) {
                character.getEquippedShield().boostPower();
            } else {
                if (character == player) {
                    System.out.println("Equip a weapon or shield first.");
                }
                return false;
            }
        } else {
            return false;
        }

        character.removeItem(itemName);
        if (character == player) {
            System.out.println("Potion used.");
        }
        return true;
    }

    private static boolean hasWon() {
        return player.getLocation() == world.getEscapeGate() && player.findItem("Source Code") != null;
    }

    private static void moveCharacterToAdjacentLocation(Character character) {
        Location currentLocation = character.getLocation();
        if (currentLocation == null || currentLocation.getExits().isEmpty()) {
            return;
        }
        Location destination = currentLocation.getExits().values().iterator().next();
        if (character instanceof Enemy enemy) {
            currentLocation.getCharacters().remove(enemy);
            destination.getCharacters().add(enemy);
        }
        character.setLocation(destination);
    }
}
