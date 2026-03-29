import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {
    private Map<String, Location> locations;
    private Location startingLocation;

    public World() {
        this.locations = new HashMap<>();
        initializeWorld();
    }

    private void initializeWorld() {
        // Creazione Location
        Location armeria = new Location("\n[ARMORY]\nA dark room with neon lights.", new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        Location laboratorio = new Location("\n[LABORATORY]\nScientific equipment and broken screens.", new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        Location serverRoom = new Location("\n[SERVER ROOM]\nCables and the humming of servers.", new ArrayList<>(), new ArrayList<>(), new HashMap<>());
        Location uscita = new Location("\n[EXIT]\nA digital portal to freedom.", new ArrayList<>(), new ArrayList<>(), new HashMap<>());

        // Aggiunta oggetti e nemici
        armeria.getItems().add(new Weapon(5, true, true, 15, 100));
        armeria.getItems().add(new Potion(1, true, true, "health", 50));

        laboratorio.getCharacters().add(new Enemy("Guard", 60, 15, 10, 30));
        
        serverRoom.getCharacters().add(new Enemy("Drone", 40, 20, 5, 40));

        uscita.getCharacters().add(new Enemy("CORE GUARDIAN", 120, 25, 15, 100));

        // Collegamenti
        connect(armeria, Direction.NORTH, laboratorio);
        connect(armeria, Direction.EAST, serverRoom);
        connect(laboratorio, Direction.EAST, uscita);
        connect(serverRoom, Direction.NORTH, uscita);

        locations.put("armory", armeria);
        locations.put("laboratory", laboratorio);
        locations.put("serverroom", serverRoom);
        locations.put("exit", uscita);

        startingLocation = armeria;
    }

    private void connect(Location from, Direction dir, Location to) {
        from.getExits().put(dir.name().toLowerCase(), to);
        to.getExits().put(dir.getOpposite().name().toLowerCase(), from);
    }

    public Location getStartingLocation() { return startingLocation; }
    public Map<String, Location> getLocations() { return locations; }

    public void lookAround(Location loc) {
        System.out.println(loc.getDescription());
        if (!loc.getItems().isEmpty()) System.out.println("Items: " + loc.getItems());
        if (!loc.getCharacters().isEmpty()) System.out.println("Enemies: " + loc.getCharacters());
        System.out.println("Exits: " + loc.getExits().keySet());
    }

    public Location getExit(Location loc, Direction dir) {
        return loc.getExits().get(dir.name().toLowerCase());
    }
}