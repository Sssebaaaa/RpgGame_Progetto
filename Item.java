abstract class Item{
    protected int weight;
    protected boolean isTransportable;
    protected boolean isDestructible;
    public Item(int weight, boolean isTransportable, boolean isDestructible){
        this.weight=weight;
        this.isTransportable=isTransportable;
        this.isDestructible=isDestructible;
    }
    public int getWeight(){
        return this.weight;
    }
    public boolean getIsTransportable(){
        return this.isTransportable;
    }
    public boolean getIsDestructible(){
        return this.isDestructible;
    }
    public void setWeight(int weight){
        this.weight=weight;
    }
    public void setIsTransportable(boolean isTransportable){
        this.isTransportable=isTransportable;
    }
    public void setIsDestructible(boolean isDestructible){
        this.isDestructible=isDestructible;
    }
    @Override
    public String toString(){
        return "Item: [Weight: "+this.weight+", is transportable: "+this.isTransportable+", is destructible: "+this.isDestructible+"]";
    }
}