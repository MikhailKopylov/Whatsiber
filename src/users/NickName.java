package users;

import java.util.Objects;

public class NickName {

    private final String nick;

    public NickName(String nick) {
        this.nick = nick;
    }

    String getNick() {
        return nick;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NickName)) return false;
        NickName nickName = (NickName) o;
        return getNick().equals(nickName.getNick());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNick());
    }

    @Override
    public String toString() {
        return nick;
    }
}
