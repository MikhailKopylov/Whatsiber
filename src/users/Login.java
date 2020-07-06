package users;

import java.util.Objects;

public class Login {

    private final String login;

    public Login(String login) {
        this.login = login;
    }

    String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Login)) return false;
        Login login1 = (Login) o;
        return getLogin().equals(login1.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }
}
