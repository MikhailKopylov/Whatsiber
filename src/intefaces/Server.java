package intefaces;

public interface Server {


    void broadcastMessage(String message);
    void sendMessagePrivate(String message, ClientHandler from, String nickRecipient);
    void subscribe(ClientHandler clientHandler);
    void unsubscribe(ClientHandler clientHandler);
    void broadcastUserList();
}
