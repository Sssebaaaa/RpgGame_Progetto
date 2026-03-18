class Potion extends Item{
    protected String effectType;
    protected int potency;
    public Potion(int weight, boolean isTransportable, boolean isDestructible, String effectType, int potency){
        super(weight, isTransportable, isDestructible);
        this.effectType=effectType;
        this.potency=potency;
    }
    public String getEffectType(){
        return this.effectType;
    }
    public int getPotency(){
        return this.potency;
    }
    public void setEffectType(String effectType){
        this.effectType=effectType;
    }
    public void setPotency(int potency){
        this.potency=potency;
    }
    @Override
    public String toString(){
        return "Potion: [Effect: "+this.effectType+", potency: "+this.potency+"]";
    }
}