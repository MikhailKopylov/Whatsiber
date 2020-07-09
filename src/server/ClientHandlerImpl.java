package server;

import intefaces.Authentication;
import intefaces.ClientHandler;
import intefaces.Server;
import intefaces.UsersOnline;
import users.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

import static server.Commands.*;

public class ClientHandlerImpl implements ClientHandler {

    private final Server server;
    private final Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    private final Authentication authentication;
    private final UsersOnline usersOnline;
    private UserData user;


    public Authentication getAuthentication() {
        return authentication;
    }

    public ClientHandlerImpl(Server server, Socket socket, UsersOnline usersOnline) {
        this.server = server;
        this.socket = socket;
        this.usersOnline = usersOnline;
        authentication = new AuthSimple();

        initializeStreams();
        Thread waitMessage = new Thread(() -> {
            checkAuthenticating();
            readIncomingMessage();
        });
        waitMessage.setDaemon(true);
        waitMessage.start();
    }


    private void initializeStreams() {
        try {
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
            System.out.println("Send msg run");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkAuthenticating() {
        while (true) {
            try {
                String authMsg = in.readUTF();
                if (authMsg.startsWith(CHECK_AUTH.toString())) {
                    user = authenticating(authMsg);
                    if (user != null) {
                        if (!usersOnline.isUserOnline(user)) {
                            authOK(user);
                            break;
                        } else {
                            sendMessage(ONLINE_WRONG.toString() + user.getNick());
                            System.out.println(ONLINE_WRONG);
                        }
                    } else {
                        sendMessage(AUTH_WRONG.toString());
                        System.out.println(AUTH_WRONG);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    private void authOK(UserData user) {
        sendMessage(AUTH_OK.toString());
        server.subscribe(this);
        server.broadcastMessage(USER_ONLINE.toString() + user.getNick());
        usersOnline.addUserOnline(user);
    }

    @Override
    public void readIncomingMessage() {
        while (true) {
            try {
                String incomingMsg = in.readUTF();
                if (incomingMsg.startsWith("/")) {
                    if (incomingMsg.startsWith(EXIT.toString())) {
                        break;
                    } else {
                        parseCommandMessage(incomingMsg);
                    }
                } else {
                    server.broadcastMessage(user.getNick() + ": " + incomingMsg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        exit();
    }

    private void parseCommandMessage(String incomingMsg) {
        String[] token = incomingMsg.split("\\s+", 3);
        String nickName = token[1];
        String message = token[2];
        Commands command = Commands.convertToCommand(token[0]);
        switch (Objects.requireNonNull(command)) {
            case PRIVATE_MESSAGE:
                if (authentication.isUserExists(nickName)) {
                    server.sendMessagePrivate(message, this, nickName);
                } else {
                    sendMessage(String.format("%s - такого пользователя не существует", nickName));
                }
                break;
            case REGISTRATION:

                break;

            default:
                throw new IllegalStateException("Unexpected command: " + token[0]);
        }
    }

    private void exit() {
        try {
            sendMessage(EXIT.toString());
            server.unsubscribe(this);
            server.broadcastMessage(String.format("%s покинул чат", user.getNick()));
            usersOnline.removeUserOnline(user);
        } finally {
            try {
                socket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private UserData authenticating(String incomingMsg) {

        String[] token = incomingMsg.split("\\s");
        Login login = new Login(token[1]);
        Password pass = new Password(token[2]);
        return getAuthentication().getUserAuth(login, pass);

    }

    @Override
    public UserData getUser() {
        return user;
    }
}
