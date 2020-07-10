package intefaces;

import users.Login;
import users.NickName;
import users.Password;
import users.UserData;

public interface Authentication {
        UserData getUserAuth(Login login, Password password);
        boolean isUserExists(String nickName);
        boolean registration(Login login, Password pass, NickName nickName);

}
