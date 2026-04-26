public class Potion extends Item {
    private final String effectType;
    private final int potency;

    public Potion(String name, String description, int weight, String effectType, int potency) {
        super(name, description, weight, true, true);
        this.effectType = effectType.toLowerCase();
        this.potency = Math.max(1, potency);
    }

    public String getEffectType() {
        return effectType;
    }

    public int getPotency() {
        return potency;
    }

    @Override
    public String getShortDescription() {
        return getName() + " - " + effectType + " potion (" + potency + ")";
    }
}
