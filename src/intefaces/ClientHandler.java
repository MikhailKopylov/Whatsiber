package intefaces;

import users.UserData;

public interface ClientHandler {

    void checkAuthenticating();
    void sendMessage(String message);
    void readIncomingMessage();
    UserData getUser();
}
