import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final List<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public int getCurrentWeight() {
        int total = 0;
        for (Item item : items) {
            total += item.getWeight();
        }
        return total;
    }

    public boolean canAdd(Item item, int maxWeight) {
        return item != null && getCurrentWeight() + item.getWeight() <= maxWeight;
    }

    public boolean addItem(Item item, int maxWeight) {
        if (!canAdd(item, maxWeight)) {
            return false;
        }
        items.add(item);
        return true;
    }

    public Item findItem(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        for (Item item : items) {
            if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                return item;
            }
        }
        return null;
    }

    public Item removeItem(String name) {
        Item item = findItem(name);
        if (item != null) {
            items.remove(item);
        }
        return item;
    }

    public String describe() {
        if (items.isEmpty()) {
            return "Inventory is empty.";
        }
        StringBuilder builder = new StringBuilder();
        for (Item item : items) {
            builder.append("- ").append(item.getShortDescription()).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
