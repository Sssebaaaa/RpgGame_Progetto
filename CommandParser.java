
public class CommandParser {

    protected String command;


    private void capisciFunzione () {
        String com = this.command.toLowerCase();
        com = com.stripLeading();
        String basicCom = com.substring(0, 3);
        for(int i = 0; i < com.length(); i++) {
            if(com.charAt(i) == ' ') {
                com = com.substring(0, i-1);
            }
        }

        switch (com) {
            case "look" :

                break;
            case "go" :

                break;

            case "pick" :

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