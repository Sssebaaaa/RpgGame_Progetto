import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Location extends Entity {
    private final List<Item> items;
    private final List<Character> characters;
    private final Map<Direction, Location> exits;

    public Location(String name, String description) {
        super(name, description);
        this.items = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.exits = new EnumMap<>(Direction.class);
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public Map<Direction, Location> getExits() {
        return exits;
    }

    public void addExit(Direction direction, Location destination) {
        exits.put(direction, destination);
    }

    public String describe() {
        return getName() + System.lineSeparator() + getDescription();
    }
}
