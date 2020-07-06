package users;

import java.util.Objects;

public class UserData {
    private final Login login;
    private final Password password;
    private final NickName nick;

    public UserData(Login login, Password password, NickName nick) {
        this.login = login;
        this.password = password;
        this.nick = nick;
    }

    public Login getLogin() {
        return login;
    }

    Password getPassword() {
        return password;
    }

    public NickName getNick() {
//        if(login.equals(this.login) && password.equals(this.password)) {
            return nick;
//        }
//        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;
        UserData userData = (UserData) o;
        return getLogin().equals(userData.getLogin()) &&
                getPassword().equals(userData.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword());
    }
}
