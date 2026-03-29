import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private int maxCapacity;

    public Inventory(int maxCapacity) {
        this.items = new ArrayList<>();
        this.maxCapacity = maxCapacity;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public boolean addItem(Item item) {
        if (item == null) return false;
        if (items.size() >= maxCapacity) {
            return false;
        }
        items.add(item);
        return true;
    }

    public Item removeItem(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).toString().toLowerCase().contains(itemName.toLowerCase())) {
                return items.remove(i);
            }
        }
        return null;
    }

    public Item removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.remove(index);
        }
        return null;
    }

    public boolean contains(String itemName) {
        for (Item item : items) {
            if (item.toString().toLowerCase().contains(itemName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.toString().toLowerCase().contains(itemName.toLowerCase())) {
                return item;
            }
        }
        return null;
    }

    public Item getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public int size() {
        return items.size();
    }

    public int getAvailableSlots() {
        return maxCapacity - items.size();
    }

    public boolean isFull() {
        return items.size() >= maxCapacity;
    }

    public void showInventory() {
        System.out.println("\n=== INVENTARIO ===");
        if (items.isEmpty()) {
            System.out.println("L'inventario e' vuoto.");
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println("[" + i + "] " + items.get(i).toString());
            }
        }
        System.out.println("Spazio utilizzato: " + items.size() + "/" + maxCapacity);
        System.out.println("==================\n");
    }
}