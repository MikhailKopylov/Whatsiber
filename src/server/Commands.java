package server;

public enum Commands {

    EXIT("/end"), CHECK_AUTH("/auth"), AUTH_OK("/authOK"), AUTH_WRONG("/authWrong"), PRIVATE_MESSAGE("/privateMsg"),
    TRY_REG("/reg"), REG_OK("/regOk"), REG_WRONG("/regWrong"),
    USER_LIST("/userlist"), USER_ONLINE("/userOnline"), ONLINE_WRONG("/onlineWrong");


    private final String command;

    Commands(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command + " ";
    }

    static public Commands convertToCommand(String command) {
        for (int i = 0; i < Commands.values().length; i++) {
            if (command.equals(Commands.values()[i].toString().trim())) {
                return Commands.values()[i];
            }
        }
        return null;
    }

}
