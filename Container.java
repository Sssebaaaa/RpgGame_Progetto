import java.util.List;
import java.util.ArrayList;

class Container extends Item {
    protected boolean isOpen;
    protected boolean isLocked;
    protected List<Item> content;

    public Container(int weight, boolean isTransportable, boolean isDestructible) {
        super(weight, isTransportable, isDestructible);
        this.content = new ArrayList<>();
        this.isOpen = false;
        this.isLocked = false;
    }

    public List<Item> getContent() { return content; }
    public void addItem(Item item) { content.add(item); }

    public void open() {
        if (isLocked) System.out.println("It's locked!");
        else {
            isOpen = true;
            System.out.println("You opened the container.");
        }
    }

    public void destroy() {
        if (isDestructible) {
            System.out.println("Container destroyed! Items have fallen to the ground.");
            isOpen = true; // Simulates forced opening
        } else {
            System.out.println("You can't destroy this.");
        }
    }

    @Override
    public String toString() {
        return "Container (" + (isOpen ? "Open" : "Closed") + ", " + content.size() + " items)";
    }
}