public class LogUserIn {
    public static String[] existingUsernames = {"apple", "banana", "grape"};
    public static String[] existingPasswords = {"elppa", "nananab", "eparg"};

    public static boolean checkForBadUsername(String username) {
        for(String s : existingUsernames) {
            if(s == username) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkIncorrectPassword(String username, String password) {
        for(int i = 0; i < existingUsernames.length; i++) {
            if(existingUsernames[i] == username && existingPasswords[i] != password) {
                return true;
            }
        }
        return false;
    }

    public static String login(String username, String password) {
        if(checkForBadUsername(username)) {
            return "Username not recognized.";
        }
        if(checkIncorrectPassword(username, password)) {
            return "Incorrect password.";
        }
        return "Login successful.";
    }
}
