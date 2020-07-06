package intefaces;

import users.Login;
import users.Password;
import users.UserData;

public interface Authentication {
        UserData getUserAuth(Login login, Password password);
        boolean isUserExists(String nickName);
}
