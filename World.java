import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Location> locations;
    private final Location startingLocation;
    private final Location escapeGate;

    public World() {
        locations = new ArrayList<>();

        Location alley = new Location("Neon Alley",
                "Rain slides across the metal walls. A broken holo-sign flickers above a maintenance hatch.");
        Location workshop = new Location("Rust Workshop",
                "A cramped repair shop full of dismantled drones, lockers and humming batteries.");
        Location archive = new Location("Data Archive",
                "Silent servers stretch into the dark. Someone left a steel locker near the terminal wall.");
        Location laboratory = new Location("Bio Lab",
                "The air smells of coolant and chemicals. Tubes pulse with pale blue light.");
        Location core = new Location("Core Chamber",
                "The heart of Omni-Corp glows behind reinforced glass. The Source Code rests in a protected cradle.");
        Location gate = new Location("Escape Gate",
                "A freight elevator points toward the free districts outside the city.");

        connect(alley, Direction.NORTH, workshop);
        connect(workshop, Direction.EAST, archive);
        connect(workshop, Direction.NORTH, laboratory);
        connect(archive, Direction.NORTH, core);
        connect(laboratory, Direction.EAST, core);
        connect(core, Direction.NORTH, gate);

        alley.getItems().add(new Item("Scrap Badge", "A dented employee badge from a forgotten shift.", 1, true, false));
        alley.getItems().add(new Potion("Small Repair Vial", "A quick repair potion for emergency use.", 1, "cure", 18));

        Container locker = new Container("Steel Locker", "A narrow locker with a simple magnetic lock.", 25, true, true, "locker-key");
        locker.addItem(new Shield("Guard Shield", "A light riot shield used by security guards.", 4, 4, 10));
        locker.addItem(new Potion("Power Solvent", "A bright liquid that reinforces gear.", 1, "power", 6));
        workshop.getItems().add(locker);
        workshop.getItems().add(new Weapon("Pipe Blade", "A crude blade welded from scrap metal.", 4, 5, 10, 6));

        archive.getItems().add(new Item("Locker Key", "A brass keycard marked with workshop storage codes.", 1, true, false, true, "locker-key"));
        archive.getItems().add(new Potion("Corrosive Flask", "A poison mixture sealed in thick glass.", 1, "poison", 5));

        Enemy guard = new Enemy("Night Guard", "A corporate guard still loyal to Omni-Corp.", 40, 10, 8, 4, 15);
        guard.addItem(new Potion("Repair Vial", "A standard issue repair potion.", 1, "repair", 12));
        guard.addItem(new Weapon("Shock Baton", "A compact stun baton used in close combat.", 3, 4, 9, 0));
        guard.equip("Shock Baton");

        Enemy drone = new Enemy("Hunter Drone", "A fast drone with a cutting arm.", 34, 9, 9, 3, 20);
        drone.addItem(new Weapon("Cutting Arm", "A mounted blade already dulled by use.", 3, 4, 8, 0));
        drone.equip("Cutting Arm");

        Enemy warden = new Enemy("Core Warden", "The final protector of the Source Code.", 70, 12, 13, 6, 35);
        warden.addItem(new Shield("Carbon Plate", "A dense shield plate fitted to the Warden's arm.", 5, 5, 12));
        warden.equip("Carbon Plate");
        warden.addItem(new Item("Source Code", "The stolen code fragment that can free the city machines.", 1, true, false));

        workshop.getCharacters().add(guard);
        laboratory.getCharacters().add(drone);
        core.getCharacters().add(warden);

        guard.setLocation(workshop);
        drone.setLocation(laboratory);
        warden.setLocation(core);

        locations.add(alley);
        locations.add(workshop);
        locations.add(archive);
        locations.add(laboratory);
        locations.add(core);
        locations.add(gate);

        startingLocation = alley;
        escapeGate = gate;
    }

    private void connect(Location from, Direction direction, Location to) {
        from.addExit(direction, to);
        to.addExit(direction.getOpposite(), from);
    }

    public List<Location> getLocations() {
        return locations;
    }

    public Location getStartingLocation() {
        return startingLocation;
    }

    public Location getEscapeGate() {
        return escapeGate;
    }

    public Location getExit(Location location, Direction direction) {
        return location.getExits().get(direction);
    }
}
