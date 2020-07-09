package intefaces;

public interface Client {


    void sendMessage(String message);
    void readIncomingMessage();
    void sendPrivateMessage(String message, String nickRecipient);

    boolean isRun();
    String getNick();
}
