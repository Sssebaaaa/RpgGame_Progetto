public enum Direction {
    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west");

    private final String displayName;

    Direction(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Direction fromString(String str) {
        for (Direction dir : Direction.values()) {
            if (dir.displayName.equalsIgnoreCase(str) || dir.name().equalsIgnoreCase(str)) {
                return dir;
            }
        }
        return null;
    }

    public Direction getOpposite() {
        switch (this) {
            case NORTH: return SOUTH;
            case SOUTH: return NORTH;
            case EAST: return WEST;
            case WEST: return EAST;
            default: return null;
        }
    }
}