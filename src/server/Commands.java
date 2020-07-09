package server;

public enum Commands {

    EXIT("/end"),  CHECK_AUTH("/auth"), AUTH_OK("/authOK"),USER_ONLINE("/userOnline "), AUTH_WRONG("/authWrong"),
    ONLINE_WRONG("/onlineWrong "), PRIVATE_MESSAGE("/privateMsg "),
    REGISTRATION("/reg"), USER_LIST("/userlist ");



    private final String command;

    Commands(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return  command;
    }

    static public Commands convertToCommand(String command){
        for (int i = 0; i < Commands.values().length; i++) {
            if(command.equals(Commands.values()[i].toString().trim())){
                return Commands.values()[i];
            }
        }
        return null;
    }

}
