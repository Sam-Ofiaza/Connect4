public class RegisterUser {
    public static final int MIN_USER_LEN = 2;
    public static final int MAX_USER_LEN = 20;
    public static final int MIN_PSWD_LEN = 6;
    public static final int MAX_PSWD_LEN = 20;

    public static boolean checkUserLength(String username) {
        if(username == null || username.length() < MIN_USER_LEN || username.length() > MAX_USER_LEN) {
            return true;
        }
        return false;
    }

    public static boolean checkPswdLength(String password) {
        if(password == null || password.length() < MIN_PSWD_LEN || password.length() > MAX_PSWD_LEN) {
            return true;
        }
        return false;
    }

    public static boolean checkUsernameContents(String username) {
        return !username.matches("^(?=.{2,20})[a-zA-Z0-9_]*$");
    }

    public static boolean checkPasswordContents(String password) {
        return !password.matches("^(?=.{6,20})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[-!@#$%^&*+=_]).*$");
    }

    public static String registerUser(String username, String password) {
        if(checkUserLength(username)) {
            return "Username length is outside of the valid range.";
        }
        if(checkPswdLength(password)) {
            return "Password length is outside of the valid range.";
        }
        if(checkUsernameContents(username)) {
            return "Username contents are invalid.";
        }
        if(checkPasswordContents(password)) {
            return "Password contents are invalid.";
        }
        return "Username and password are valid.";
    }
}
