package intefaces;

import users.UserData;

public interface UsersOnline {

    void addUserOnline(UserData user);
    void removeUserOnline(UserData user);
    boolean isUserOnline(UserData user);
}
