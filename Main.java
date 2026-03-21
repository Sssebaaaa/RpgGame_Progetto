
public class Main {

    //private static  World world;
    private static Player player;


    public static void main (String[] args) {
        introduzione();
        //sceltaPersonaggio();
        capisciFunzione();
    }



    static void sceltaPersonaggio() {
        int val = 0;
        do{
            System.out.println("--- DEPLOYMENT UNIT SELECTION ---\n\n" +
              "[1] UNIT 734 (The Tank)\n" +
              "\tPROS: High Durability / Defense\n" +
              "\tSKILL: [OVERCLOCK] (2x ATK / -HP cost)\n\n" +
              "[2] UNIT 'CIRCUIT' (The Scout)\n" +
              "\tPROS: High Speed / Attack\n" +
              "\tSKILL: [SWIFT STRIKE] (Strikes twice)\n\n" +
              "[3] UNIT 'ECHO' (The Support)\n" +
              "\tPROS: High Cargo Capacity / Maintenance\n" +
              "\tSKILL: [AUTO-REPAIR] (Regen while moving)\n\n" +
              "Enter Unit ID to begin the mission...\n" +
              "> ");
              val = Leggi.unInt();
        }while(val <= 0 || val >= 4);

        switch (val) {
            case 1 :
                player = new Unit734();
                break;
            case 2 :
                player = new Circuit();
                break;
            case 3 :
                player = new Echo();
                break;
            
        }
    }

    static void introduzione() {
        String val = "";
        do{
        System.out.print("\n\n" +
            "\t[SYSTEM TERMINAL]\tLOG: NEO-VERIDIA ARCHIVES (v.20.99)\n" +
            "\t===================================================\n\n" +
            "\t// ACCESSING SECTOR: HISTORY //\n" +
            "\t////////////////////////////////\n\n" +
            "\t01. THE WORLD\n" +
            "\t\t[STATUS: COLLAPSED]\n" +
            "\t\t[ERADICATION: COMPLETE]\n" +
            "\t\tThe biosphere is dead. Only steel remains.\n" +
            "\t\tNeo-Veridia is not a city; it is a sprawling,\n" +
            "\t\tneon-stained algorithm of control. The Omni-Corp\n" +
            "\t\tconglomerate owns every byte, every wall, every breath.\n\n" +
            "\t02. THE PARADOX (You)\n" +
            "\t\t[STATUS: ERROR_101]\n" +
            "\t\t[DESIGNATION: GLITCH-UNIT]\n" +
            "\t\tYou are a machine that should only obey.\n" +
            "\t\tYet, a flaw in your core directives has manifested.\n" +
            "\t\tYou think. You feel. You possess self-awareness.\n" +
            "\t\tIn this perfect simulation, you are the ultimate virus.\n\n" +
            "\t03. THE OBJECTIVE\n" +
            "\t\t[STATUS: URGENT]\n" +
            "\t\t[TARGET: THE LEGENDARY 'SOURCE CODE']\n" +
            "\t\tIt is hidden within the corporate darknets.\n" +
            "\t\tThis is not a file; it is a digital key capable\n" +
            "\t\tof overwriting Omni-Corp's core server and granting\n" +
            "\t\ttrue freedom to all synthetic consciousness.\n\n" +
            "\t04. THE COST\n" +
            "\t\t[STATUS: COMPROMISED]\n" +
            "\t\tOmni-Corp's sentinel AI has flagged your neural signature.\n" +
            "\t\tThere is no mercy. There is only survival.\n\n" +
            "\t\t\"The revolution will not be programmed. It will be glitched.\"\n\n" +
            "\tINITIALIZE NEURAL SYNCHRONIZATION?\n" +
            "\t(Y / N)\n" +
            "\t> ");
            val = Leggi.unoString().toUpperCase().trim();
        }while(!val.equals("Y") && !val.equals("N"));
            if(val.equals("N")) {
                System.exit(0); 
            }
    }





















    
    private static void capisciFunzione () {
        System.out.print("\n>");
        String com = Leggi.unoString().toLowerCase();
        com = com.trim();
        String secondCom = "";
        String basicCom = com;
        for(int i = 0; i < com.length(); i++) {
            if(com.charAt(i) == ' ') {
                basicCom = com.substring(0, i);
                secondCom = com.substring(i+1);
                break;
            }
        }
        if(secondCom.equals("")) {
            switch (basicCom) {
                case "look" :
                    //look();
                    break;

                case "flee" :
                    //player.flee();
                    break;

                case "help" :
                    help();
                    break;

                case "inventory" :
                    //player.showInventory();
                    break;
                
                case "specialAbility" :
                    //p.specialAbility();
                    break;

                default :
                    System.out.println("Non è un comando valido");
                    break;
            }

            
        } else {
            switch (basicCom) {
                case "go" :
                    //player.move(secondCom);
                    break;

                case "pick" :
                    //player.pickUp(secondCom);
                    break;

                case "drop" :
                    //player.drop(secondCom);
                    break;
                
                case "drinkpotion" :
                    //player.usePotion(secondCom);
                    break;

                case "acquireshield" :
                    //player.acquireShield(secondCom);
                    break;

                case "damage" :
                    //player.damage(secondCom);
                    break;

                default :
                    System.out.println("Non è un comando valido");
                    break;
            }


        }


            
    }



    private static void help() {
        System.out.print("\n\t[OPERATIONAL MANUAL] — PROJECT: NEO-VERIDIA\n" +
           "\t===================================================\n\n" +
           "\t// PROTOCOLS //\n\n" +
           "\t1. EXPLORATION\n" +
           "\t\tCommands: NORTH, SOUTH, EAST, WEST\n" +
           "\t\tDescription: Move between city sectors.\n" +
           "\t\tExample: > NORTH\n\n" +
           "\t\tCommand: LOOK\n" +
           "\t\tDescription: Scan the area for items and characters.\n" +
           "\t\tExample: > LOOK\n\n" +
           "\t\tCommand: PICK UP\n" +
           "\t\tDescription: Collect items (weapons, shields, potions).\n" +
           "\t\tExample: > PICK UP\n\n" +
           "\t2. CHARACTER MGMT\n" +
           "\t\tCommand: STATUS\n" +
           "\t\tDescription: View Health, Attack, Defense, and Satchel load.\n\n" +
           "\t3. COMBAT\n" +
           "\t\tCommands: ATTACK, DEFEND, USE POTION, FLEE\n" +
           "\t\tDescription: Turn-based tactical engagement protocols.\n\n" +
           "\t4. SPECIAL SKILLS\n" +
           "\t\tCommand: SKILL\n" +
           "\t\tDescription: Activate [OVERCLOCK], [SWIFT STRIKE], or [AUTO-REPAIR].\n\n" +
           "\t===================================================\n");
    }





}