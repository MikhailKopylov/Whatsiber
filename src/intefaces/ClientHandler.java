package intefaces;

import users.UserData;

public interface ClientHandler {

    boolean checkAuthenticating();
    void sendMessage(String message);
    void readIncomingMessage();
    UserData getUser();
}
