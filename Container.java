import java.util.List;
import java.util.ArrayList;
class Container extends Item{
    protected boolean isOpen;
    protected boolean isLocked;
    protected List<Item> content;
    public Container(int weight, boolean isTransportable, boolean isDestructible){
        super(weight, isTransportable, isDestructible);
        this.content = new ArrayList<Item>();
        this.isOpen = false;
        this.isLocked = false;
    }
    public Container(int weight, boolean isTransportable, boolean isDestructible, boolean isOpen, boolean isLocked, List<Item> content) {
        super(weight, isTransportable, isDestructible);
        this.isOpen = isOpen;
        this.isLocked = isLocked;
        this.content = content;
    }
    public boolean getIsOpen(){
        return this.isOpen;
    }
    public boolean getisLocked(){
        return this.isLocked;
    }
    public void setIsOpen(boolean isOpen){
        this.isOpen=isOpen;
    }
    public void setIsLocked(boolean isLocked){
        this.isLocked=isLocked;
    }
    public List<Item> getContent(){
        return this.content;
    }
    public void setContent(List<Item> content){
        this.content=content;
    }
    public void addItem(Item item){
        this.content.add(item);
    }
    public void removeItem(Item item){
        this.content.remove(item);
    }
    public void showContentDetails(){
        if(this.isOpen == true || this.isLocked == false){
            System.out.println("Content details:");
            for(int i = 0; i < this.content.size(); i++){
                Item item = this.content.get(i);
                System.out.println(
                    "Item " + i +
                    " -> Weight: " + item.getWeight() +
                    ", Transportable: " + item.getIsTransportable() +
                    ", Destructible: " + item.getIsDestructible()
                );
            }
        } else {
            System.out.println("Cannot show content details.");
        }
    }
    public void open(){
        if(this.isLocked==true){
            System.out.println("Container is locked. Cannot open.");
        } else if(this.isOpen==true){
            System.out.println("Container is already open.");
        } else {
            this.isOpen = true;
            System.out.println("Container opened.");
        }
    }
    public void destroy(){
        if(!this.isDestructible==false){
            System.out.println("Container cannot be destroyed.");
        } else {
            this.content.clear();
            this.isOpen = false;
            System.out.println("Container destroyed. Content lost.");
        }
    }
    @Override
    public String toString(){
        return "Container: [is open: "+this.isOpen+", is locked: "+this.isLocked+", content size: "+this.content.size()+"]";
    }
}