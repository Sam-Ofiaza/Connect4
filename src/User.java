import java.util.Comparator;
import java.util.Objects;

public class User {
    private String username, password;
    private char token;
    private boolean loggedIn;
    private int score;

    public User(String username, String password) { this.username = username; this.password = password; this.score = 0; this.token = '\0'; }
    public User(String username, String password, int score, char token) { this.username = username; this.password = password; this.score = score; this.token = token; }

    public char getToken() { return token; }
    public void setToken(char token) { this.token = token; }

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public void update(int score) { this.score += score; }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(!(o instanceof User))
            return false;
        User other = (User)o;
        return (this.username.equals(other.getUsername())) && (this.password.equals(other.getPassword()));
    }
}
