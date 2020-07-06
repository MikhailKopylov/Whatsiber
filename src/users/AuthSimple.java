package users;

import intefaces.Authentication;

import java.util.HashMap;

public class AuthSimple implements Authentication {

    private final HashMap<Login, UserData> userDataMap;

    public AuthSimple() {
        userDataMap = new HashMap<>();

        for (int i = 1; i <= 10; i++) {
            UserData user = new UserData(new Login("l" + i), new Password("p" + i), new NickName("user" + i));
            userDataMap.put(user.getLogin(), user);
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
}
