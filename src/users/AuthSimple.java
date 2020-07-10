package users;

import intefaces.Authentication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AuthSimple implements Authentication {

    private final HashMap<Login, UserData> userDataMap;
    private final Set<NickName> nickNames;

    public AuthSimple() {
        userDataMap = new HashMap<>();
        nickNames = new HashSet<>();

        for (int i = 1; i <= 10; i++) {
            NickName nickName = new NickName("user" + i);
            addNewUser(new Login("l" + i), new Password("p" + i), nickName);
        }
    }

    @Override
    public UserData getUserAuth(Login login, Password password) {
        if(userDataMap.get(login) != null){
           UserData user =  userDataMap.get(login);
           if(user.getPassword().equals(password)){
               return user;
           }
        }
        return null;
    }

    @Override
    public boolean isUserExists(String nickName) {
        for (UserData userData : userDataMap.values()) {
            if(userData.getNick().toString().equals(nickName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean registration(Login login, Password pass, NickName nickName) {
        if(userDataMap.get(login) != null || nickNames.contains(nickName)) {
            return false;
        }
        addNewUser(login, pass, nickName);
        return true;
    }

    private void addNewUser(Login login, Password pass, NickName nickName) {
        UserData user = new UserData(login, pass, nickName);
        userDataMap.put(user.getLogin(), user);
        nickNames.add(nickName);
    }
}
