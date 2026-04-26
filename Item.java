public class Item extends Entity {
    private final int weight;
    private final boolean transportable;
    private final boolean destructible;
    private final boolean keyItem;
    private final String keyCode;

    public Item(String name, String description, int weight, boolean transportable, boolean destructible) {
        this(name, description, weight, transportable, destructible, false, "");
    }

    public Item(String name, String description, int weight, boolean transportable, boolean destructible, boolean keyItem, String keyCode) {
        super(name, description);
        this.weight = Math.max(0, weight);
        this.transportable = transportable;
        this.destructible = destructible;
        this.keyItem = keyItem;
        this.keyCode = keyCode == null ? "" : keyCode.toLowerCase();
    }

    public int getWeight() {
        return weight;
    }

    public boolean isTransportable() {
        return transportable;
    }

    public boolean isDestructible() {
        return destructible;
    }

    public boolean isKeyItem() {
        return keyItem;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public String getShortDescription() {
        return getName() + " - " + getDescription();
    }
}
