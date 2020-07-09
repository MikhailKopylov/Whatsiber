package client;

import intefaces.Client;
import server.Commands;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static server.Commands.*;

public class ClientImpl implements Client {

    private static final int PORT = 8189;
    private static final String IP_ADDRESS = "localhost";

    private final Controller controller;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String nick;

    public ClientImpl(Controller controller) {
        this.controller = controller;
        connected();
        readIncomingMessage();
    }

    private void connected() {
        try {
            socket = new Socket(IP_ADDRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPrivateMessage(String message, String nickRecipient) {
        try {
            String privateMsg = String.format("%s %s %s",
                    PRIVATE_MESSAGE, nickRecipient, message);
            out.writeUTF(privateMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readIncomingMessage() {
        Thread waitMessage = new Thread(() -> {
            try {
                while (true) {
                    String commandMsg = in.readUTF();
                    if (commandMsg.startsWith(AUTH_OK.toString())) {
                        nick = commandMsg.split("\\s")[1];
                        controller.addNewMessage(String.format("%s в сети", nick));
                        controller.setAuthorized(true);
                        break;
                    } else if(commandMsg.startsWith(AUTH_WRONG.toString())){
                        controller.addNewMessage("Неверное имя пользователя или пароль");
                    } else if (commandMsg.startsWith(ONLINE_WRONG.toString())){
                        String nickOnlineWrong = commandMsg.split("\\s")[1];
                        controller.addNewMessage(String.format("%s уже в сети", nickOnlineWrong ));
                    }

                }
                while (true) {
                    String incomingMsg = in.readUTF();

                    if (incomingMsg.startsWith(AUTH_OK.toString())) {
                        String newUserNick = incomingMsg.split("\\s")[1];
                        controller.addNewMessage(String.format("%s в сети", newUserNick));
                    } else if (incomingMsg.startsWith(EXIT.toString())){
                        break;
                    } else {
                        controller.addNewMessage(incomingMsg);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        waitMessage.setDaemon(true);
        waitMessage.start();
    }

    @Override
    public boolean isRun() {
        return socket != null && !socket.isClosed();
    }

    @Override
    public String getNick() {
        if(nick != null) {
            return nick;
        } else {
            return "";
        }
    }
}
