import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Location{
    public String description;
    public List <Item> items;
    public List <Character> characters;
    public HashMap <String, Location> exits;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    public List<Character> getCharacters() {
        return characters;
    }
    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
    public HashMap<String, Location> getExits() {
        return exits;
    }
    public void setExits(HashMap<String, Location> exits) {
        this.exits = exits;
    }
    public Location(String description, List<Item> items, List<Character> characters, HashMap<String, Location> exits) {
        this.description = description;
        this.items = items;
        this.characters = characters;
        this.exits = exits;
    }

    
}