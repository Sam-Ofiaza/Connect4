public class Player {
    private String name;
    private char token;
    private User user;

    public Player(String name, char token) { this.name = name; this.token = token; user = null; }
    public Player(String name, char token, User user) { this.name = name; this.token = token; this.user = user; }

    public void setName(String name) { this.name = name; }
    public void setToken(char token) { this.token = token; }
    public void setUser(User user) { this.user = user; }

    public String getName() { return name; }
    public char getToken() { return token; }
    public User getUser() { return user; }

    public String toString() {
        return name;
    }
}
