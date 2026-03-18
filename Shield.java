class Shield extends Item{
    protected int defense;
    protected int durability;
    public Shield(int weight, boolean isTransportable, boolean isDestructible, int defense, int durability){
        super(weight, isTransportable, isDestructible);
        this.defense=defense;
        this.durability=durability;
    }
    public int getDefense(){
        return this.defense;
    }
    public int getDurability(){
        return this.durability;
    }
    public void setDefense(int defense){
        this.defense=defense;
    }
    public void setDurability(int durability){
        this.durability=durability;
    }
    @Override
    public String toString(){
        return "Shield: [Defense: "+this.defense+", durability: "+this.durability+"]";
    }
}