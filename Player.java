public class Player extends Character {
    protected Location currentLocation;
    protected World world;
    protected int experience;

    public Player(String name, int maxHealth, int attack, int defense, int inventoryCapacity) {
        super(name, maxHealth, attack, defense, inventoryCapacity);
        this.experience = 0;
    }

    public void setLocation(Location loc) { this.currentLocation = loc; }
    public Location getLocation() { return currentLocation; }
    public void setWorld(World w) { this.world = w; }
    public int getExperience() { return experience; }
    public void addExperience(int xp) { this.experience += xp; }

    public void move(Direction dir) {
        Location next = world.getExit(currentLocation, dir);
        if (next != null) {
            currentLocation = next;
            System.out.println("You move " + dir.getDisplayName() + ".");
            System.out.println(currentLocation.getDescription());
        } else {
            System.out.println("You can't go that way.");
        }
    }

    public void pickUp(String itemName) {
        Item item = null;
        for (Item i : currentLocation.getItems()) {
            if (i.toString().toLowerCase().contains(itemName.toLowerCase())) {
                item = i; break;
            }
        }
        if (item != null && !inventory.isFull()) {
            inventory.addItem(item);
            currentLocation.getItems().remove(item);
            System.out.println("Picked up: " + item);
        } else {
            System.out.println("You can't pick that up.");
        }
    }

    public void drop(String itemName) {
        Item item = inventory.removeItem(itemName);
        if (item != null) {
            currentLocation.getItems().add(item);
            if (item == equippedWeapon) equippedWeapon = null;
            if (item == equippedShield) equippedShield = null;
            System.out.println("Dropped: " + item);
        }
    }

    public void equipWeapon(String itemName) {
        Item i = inventory.getItem(itemName);
        if (i instanceof Weapon) {
            equippedWeapon = (Weapon) i;
            System.out.println("Equipped weapon: " + i);
        }
    }

    public void equipShield(String itemName) {
        Item i = inventory.getItem(itemName);
        if (i instanceof Shield) {
            equippedShield = (Shield) i;
            System.out.println("Equipped shield: " + i);
        }
    }

    public void usePotion(String itemName) {
        Item i = inventory.getItem(itemName);
        if (!(i instanceof Potion)) {
            System.out.println("No such potion.");
            return;
        }
        Potion p = (Potion) i;
        String type = p.getEffectType().toLowerCase();
        switch (type) {
            case "health", "cure" -> {
                health.add(p.getPotency());
                System.out.println("HP restored! HP: " + health.getCurrent() + "/" + health.getMax());
            }
            case "poison" -> {
                health.setMax(Math.max(1, health.getMax() - p.getPotency()));
                health.subtract(p.getPotency());
                System.out.println("Poison! HP: " + health.getCurrent() + "/" + health.getMax());
            }
            case "power" -> {
                if (equippedWeapon != null) {
                    equippedWeapon.setDamage((int)(equippedWeapon.getDamage() * 1.3));
                    equippedWeapon.setDurability(100);
                    System.out.println("Weapon boosted! " + equippedWeapon);
                } else if (equippedShield != null) {
                    equippedShield.setDefense((int)(equippedShield.getDefense() * 1.3));
                    equippedShield.setDurability(100);
                    System.out.println("Shield boosted! " + equippedShield);
                } else {
                    System.out.println("Nothing equipped to boost.");
                    return;
                }
            }
            default -> { System.out.println("Unknown potion type."); return; }
        }
        inventory.removeItem(itemName);
    }

    public void specialAbility() { System.out.println("No special ability."); }
}