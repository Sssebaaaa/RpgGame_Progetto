
public class Main {

    protected World world;
    protected Player player;


    public static void main (String[] args) {
        sceltaPersonaggio();
    }



    private void sceltaPersonaggio() {
        do{
            System.out.println("--- SELEZIONE UNITÀ OPERATIVA ---

            [1] UNIT 734 (The Tank)
                PROS: Alta Resistenza / Difesa
                SKILL: [OVERCLOCK] (2x ATK / -HP)

            [2] UNIT 'CIRCUIT' (The Scout)
                PROS: Velocità / Attacco Elevato
                SKILL: [SWIFT STRIKE] (Colpisce 2 volte)

            [3] UNIT 'ECHO' (The Support)
                PROS: Capacità Carico / Manutenzione
                SKILL: [AUTO-REPAIR] (Regen durante spostamento)

            Digitare ID unità per iniziare la missione... 
            >")
        }while();
    }




















    private void capisciFunzione (Player p) {
        String com = this.command.toLowerCase();
        com = com.stripLeading().stripTrailing();
        secondCom = "";
        String basicCom = com.substring(0, 3);
        for(int i = 0; i < com.length(); i++) {
            if(com.charAt(i) == ' ') {
                com = com.substring(0, i-1);
                secondCom = com.substring(i);
                break;
            }
        }

        switch (com) {
            case "look" :
                look();
                break;
            case "go" :
                p.move(secondCom);
                break;

            case "pick" :
                p.pickUp(secondCom)
                break;

            case "drop" :
                p.drop()
                break;

            case "flee" :
                p.flee()
                break;

            default :
                System.out.println("Non è un comando valido");
        }
    }



    public String getCommand() {
        return command;
    }



    public void setCommand(String command) {
        this.command = command;
    } 

}