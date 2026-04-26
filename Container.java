import java.util.ArrayList;
import java.util.List;

public class Container extends Item {
    private final List<Item> contents;
    private boolean open;
    private boolean locked;
    private final String keyCode;

    public Container(String name, String description, int weight, boolean destructible, boolean locked, String keyCode) {
        super(name, description, weight, false, destructible);
        this.contents = new ArrayList<>();
        this.open = false;
        this.locked = locked;
        this.keyCode = keyCode == null ? "" : keyCode.toLowerCase();
    }

    public List<Item> getContents() {
        return contents;
    }

    public void addItem(Item item) {
        contents.add(item);
    }

    public boolean isOpen() {
        return open;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean canUnlockWith(Item item) {
        return item != null && item.isKeyItem() && item.getKeyCode().equals(keyCode);
    }

    public boolean unlock(Item item) {
        if (!locked) {
            return true;
        }
        if (canUnlockWith(item)) {
            locked = false;
            return true;
        }
        return false;
    }

    public boolean open() {
        if (locked) {
            return false;
        }
        open = true;
        return true;
    }

    public void close() {
        open = false;
    }

    public List<Item> emptyContents() {
        List<Item> dropped = new ArrayList<>(contents);
        contents.clear();
        return dropped;
    }

    @Override
    public String getShortDescription() {
        String state = locked ? "locked" : open ? "open" : "closed";
        return getName() + " - " + state;
    }
}
