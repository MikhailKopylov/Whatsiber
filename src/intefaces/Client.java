package intefaces;

import users.Password;
import users.UserData;

public interface Client {


    void sendMessage(String message);
    void readIncomingMessage();
    void sendPrivateMessage(String message, String nickRecipient);

    boolean isRun();
    String getNick();
    void tryToReg(UserData user, Password password);
}
