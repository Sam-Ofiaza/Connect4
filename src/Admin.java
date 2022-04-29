public class Admin extends User{

    public Admin(String username, String password) {
        super(username, password);
    }
    public Admin(String username, String password, int score, char token) {
        super(username, password, score, token);
    }

}
