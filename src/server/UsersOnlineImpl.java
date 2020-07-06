package server;

import intefaces.UsersOnline;
import users.UserData;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class UsersOnlineImpl implements UsersOnline {

    private final Set<UserData> usersOnline;

    public UsersOnlineImpl() {
        usersOnline = new CopyOnWriteArraySet<>();
    }

    @Override
    public void addUserOnline(UserData user) {
        usersOnline.add(user);
    }

    @Override
    public void removeUserOnline(UserData user) {
        usersOnline.remove(user);
    }

    @Override
    public boolean isUserOnline(UserData user) {
        return usersOnline.contains(user);
    }
}
