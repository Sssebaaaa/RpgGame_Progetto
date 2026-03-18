class Weapon extends Item{
    protected int damage;
    protected int durability;
    public Weapon(int weight, boolean isTransportable, boolean isDestructible, int damage, int durability){
        super(weight, isTransportable, isDestructible);
        this.damage=damage;
        this.durability=durability;
    }
    public int getDamage(){
        return this.damage;
    }
    public int getDurability(){
        return this.durability;
    }
    public void setDamage(int damage){
        this.damage=damage;
    }
    public void setDurability(int durability){
        this.durability=durability;
    }
    @Override
    public String toString(){
        return "Weapon: [Damage: "+this.damage+", durability: "+this.durability+"]";
    }
}