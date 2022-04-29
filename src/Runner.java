import java.util.Scanner;

import static java.lang.Character.isUpperCase;

public class Runner {
    public static void main(String[] args) {
        MainSys mainSys = new MainSys();
        GameMgmt gamemgmt = new GameMgmt();
        LeaderboardSys leaderboardSys = new LeaderboardSys();
        FeedbackSys feedbackSys = new FeedbackSys();

        Scanner keyboard = new Scanner(System.in);
        String input;
        boolean stop = false;
        System.out.println("Welcome to Connect 4.\nType 'back' to go back or 'stop' to end this session at any time.");
        while(!stop) { // MAIN MENU
            System.out.print("Sign in (s), register (r), play (p), or view leaderboard (v)? " );
            input = keyboard.next();
            while(!validMainMenuSelection(input) && !containsBackOrStop(input)) {
                System.out.print("Please enter 's' 'r' 'p' or 'l': ");
                input = keyboard.next();
            }
            if(input.equals("stop")) {
                stop = true;
            }
            else if(input.equals("back")) {
                System.out.println("There is no previous page.");
            }
            else if(input.equals("s")) { // SIGN IN
                boolean invalid = true;
                User current = null;
                while(invalid) {
                    String username, password;
                    System.out.print("Enter a username: ");
                    username = keyboard.next();

                    System.out.print("Enter a password: ");
                    password = keyboard.next();

                    if(!mainSys.logUserIn(username, password))
                        System.out.println("Invalid login attempt - please try again.");
                    else {
                        invalid = false;
                        current = mainSys.getUser(username, password);
                        System.out.println("Login successful. Redirecting to your homepage...");
                    }
                }
                boolean loggedIn = true;
                if(current instanceof Admin) {
                    while(loggedIn && !stop) { // ADMIN HOMEPAGE
                        System.out.print(current.getUsername() + "'s homepage and admin menu.\nPlay (p), view leaderboard (vl), change your settings (c), view feedback messages (vf), access all user settings (a), or log out (l)? ");
                        input = keyboard.next();
                        while(!validAdminHomepageSelection(input) && !containsBackOrStop(input)) {
                            System.out.print("Please enter 'p', 'vl', 'c', 'vf', 'a', or 'l': ");
                            input = keyboard.next();
                        }
                        if(input.equals("stop")) {
                            stop = true;
                        }
                        else if(input.equals("back")) {
                            continue;
                        }

                        if(input.equals("p")) { // PLAY
                            boolean quit = false;
                            while(!quit) {
                                char token = '\0';

                                if(current.getToken() == '\0') {
                                    System.out.print("Enter a token: ");
                                    input = keyboard.next();
                                    while(!validToken(input) && !containsBackOrStop(input)) {
                                        System.out.print("The token should be a single uppercase character.\nEnter a token: ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }
                                    token = input.charAt(0);
                                }
                                else {
                                    token = current.getToken();
                                }

                                Player p = new Player(current.getUsername(), token);
                                GameResult result = gamemgmt.startGame(p);
                                System.out.println(result.getWinner() + " beat " + result.getLoser() + " with a score of " + result.getScore());

                                System.out.print("Play again (p) or quit (q)? ");
                                input = keyboard.next();
                                while(!validPlayOrQuit(input) && !containsBackOrStop(input)) {
                                    System.out.print("Please enter 'p' or 'q': ");
                                    input = keyboard.next();
                                }
                                if(input.equals("stop")) {
                                    stop = true;
                                }
                                else if(input.equals("q")) {
                                    quit = true;
                                }
                            }
                        }
                        else if(input.equals("vl")) { // VIEW LEADERBOARD
                            System.out.println("Leaderboard:\n" + leaderboardSys.displayLeaderboard());
                        }
                        else if(input.equals("c")) { // CHANGE OWN SETTINGS
                            boolean quit = false;
                            while(!quit && !stop) {
                                System.out.print("User profile settings.\nChange username (u), change password (p), or change token (t)? ");
                                input = keyboard.next();
                                while(!validChangeOwnSettingsSelection(input) && !containsBackOrStop(input)) {
                                    System.out.print("Please enter 'u', 'p', or 't': ");
                                    input = keyboard.next();
                                }
                                if(input.equals("stop")) {
                                    stop = true;
                                }
                                else if(input.equals("back")) {
                                    continue;
                                }

                                if(input.equals("u")) { // CHANGE USERNAME
                                    String username;
                                    System.out.print("Enter a new username: ");
                                    // TODO: Check if new username is taken
                                    input = keyboard.next();
                                    while(!validUsername(input) && !containsBackOrStop(input)) {
                                        System.out.print("The username must be 3-20 characters long and should contain lowercase and uppercase letters, digits, and underscores only.\nEnter a new username: ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }
                                    username = input;


                                    current.setUsername(username);
                                    System.out.println("Username successfully changed. Redirecting to your settings menu...");
                                }
                                else if(input.equals("p")) { // CHANGE PASSWORD
                                    System.out.print("Enter your old password: ");
                                    input = keyboard.next();
                                    while(!current.getPassword().equals(input) && !containsBackOrStop(input)) {
                                        System.out.print("Password doesn't match.\nTry again: ");
                                        input = keyboard.next();
                                    }

                                    String pswd1, pswd2;
                                    System.out.print("Enter a new password: ");
                                    input = keyboard.next();
                                    while(!validPassword(input) && !containsBackOrStop(input)) {
                                        System.out.print("The password must be 6-20 characters long and should have at least one lowercase letter, one uppercase letter, one digit, and one special character.\nEnter a new password: ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }
                                    pswd1 = input;

                                    System.out.print("Confirm new password: ");
                                    input = keyboard.next();
                                    while(!pswd1.equals(input)) {
                                        System.out.print("Password doesn't match.\nTry again: ");
                                        input = keyboard.next();
                                    }
                                    pswd2 = input;

                                    current.setPassword(pswd2);
                                    System.out.println("Password successfully changed. Redirecting to your settings menu...");
                                }
                                else if(input.equals("t")) { // CHANGE TOKEN
                                    char token;
                                    System.out.print("Enter a new token: ");
                                    input = keyboard.next();
                                    while(!validToken(input) && !containsBackOrStop(input)) {
                                        System.out.print("The token should be a single uppercase character.\nEnter a token: ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }
                                    token = input.charAt(0);

                                    current.setToken(token);
                                    System.out.println("Token successfully changed. Redirecting to your settings menu...");
                                }
                            }
                        }
                        else if(input.equals("vf")) { // VIEW FEEDBACK MESSAGES
                            System.out.println(feedbackSys.viewMessages());
                        }
                        else if(input.equals("a")) { // ACCESS ALL USER SETTINGS
                            boolean quit = false;
                            while(!quit & !stop) {
                                System.out.print("All user settings.\nAdd user/admin (a), change user attributes (c), or remove user (r)? ");
                                input = keyboard.next();
                                while(!validAllUserSettingsSelection(input) && !containsBackOrStop(input)) {
                                    System.out.print("Please enter 'a', 'c', or 'r': ");
                                    input = keyboard.next();
                                }
                                if(input.equals("stop")) {
                                    stop = true;
                                }
                                else if(input.equals("back")) {
                                    continue;
                                }

                                if(input.equals("a")) { // ADD USER
                                    String username, password;
                                    int score = -1;
                                    char token = '\0';
                                    System.out.print("Enter a username: ");
                                    input = keyboard.next();
                                    while(!validUsername(input) && !containsBackOrStop(input)) {
                                        System.out.print("The username must be 3-20 characters long and should contain lowercase and uppercase letters, digits, and underscores only.\nEnter a username: ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }
                                    username = input;

                                    System.out.print("Enter a password: ");
                                    input = keyboard.next();
                                    while(!validPassword(input) && !containsBackOrStop(input)) {
                                        System.out.print("The password must be 6-20 characters long and should have at least one lowercase letter, one uppercase letter, one digit, and one special character.\nEnter a password: ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }
                                    password = input;
                                    // TODO: Check if user already exists

                                    User u = null;
                                    System.out.print("Would you also like to add this user's score and token? (y/n): ");
                                    input = keyboard.next();
                                    while(!validYesOrNo(input) && !containsBackOrStop(input)) {
                                        System.out.print("Enter 'y' or 'n': ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }

                                    if(input.equals("y")) {
                                        System.out.print("Enter a score: ");
                                        input = keyboard.next();
                                        while(!validScore(input) && !containsBackOrStop(input)) {
                                            System.out.print("The score should be an integer greater than or equal to zero.\nEnter a score: ");
                                            input = keyboard.next();
                                        }
                                        if(input.equals("stop")) {
                                            stop = true;
                                            continue;
                                        }
                                        else if(input.equals("back")) {
                                            continue;
                                        }
                                        score = Integer.parseInt(input);

                                        System.out.print("Enter a single-character token: ");
                                        input = keyboard.next();
                                        while(!validToken(input) && !containsBackOrStop(input)) {
                                            System.out.print("The token should be a single uppercase character.\nEnter a token: ");
                                            input = keyboard.next();
                                        }
                                        if(input.equals("stop")) {
                                            stop = true;
                                            continue;
                                        }
                                        else if(input.equals("back")) {
                                            continue;
                                        }
                                        token = input.charAt(0);

                                    }
                                    System.out.print("Would you like to make this user an admin? (y/n): ");
                                    input = keyboard.next();
                                    while(!validYesOrNo(input) && !containsBackOrStop(input)) {
                                        System.out.print("Enter 'y' or 'n': ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }

                                    if(input.equals("y")) {
                                        if(score != -1 && token != '\0') {
                                            u = new Admin(username, password, score, token);
                                        }
                                        else {
                                            u = new Admin(username, password);
                                        }
                                        mainSys.addUser(u);
                                        System.out.println("New admin successfully added. Redirecting to the user settings menu...");
                                    }
                                    else if(input.equals("n")) {
                                        if(score != -1 && token != '\0') {
                                            u = new User(username, password, score, token);
                                        }
                                        else {
                                            u = new User(username, password);
                                        }
                                        mainSys.addUser(u);
                                        System.out.println("New user successfully added. Redirecting to the user settings menu...");
                                    }
                                }
                                else if(input.equals("c")) { // CHANGE USER ATTRIBUTES
                                    boolean quit2 = false;
                                    while(!quit2 && !stop) {
                                        boolean invalid2 = true;
                                        String username, password;
                                        User u = null;
                                        System.out.println("Change user attributes.");
                                        while(invalid2) {
                                            System.out.print("Enter an existing username: ");
                                            username = keyboard.next();
                                            System.out.print("Enter a password: ");
                                            password = keyboard.next();

                                            if((u = mainSys.getUser(username, password)) == null) {
                                                System.out.println("User not recognized, try again.");
                                            }
                                            else {
                                                invalid2 = false;
                                            }
                                        }

                                        System.out.print("User identified.\nChange user's username (u), password (p), token (t), or score (s)? ");
                                        input = keyboard.next();
                                        while(!validChangeUserAttributesSelection(input) && !containsBackOrStop(input)) {
                                            System.out.print("Please enter 'u', 'p', 't', or 's': ");
                                            input = keyboard.next();
                                        }
                                        if(input.equals("stop")) {
                                            stop = true;
                                        }
                                        else if(input.equals("back")) {
                                            continue;
                                        }

                                        if(input.equals("u")) { // CHANGE USER'S USERNAME
                                            String newUsername;
                                            System.out.print("Enter a username to replace this user's existing username: ");
                                            // TODO: Check if new username is taken
                                            input = keyboard.next();
                                            while(!validUsername(input) && !containsBackOrStop(input)) {
                                                System.out.print("The username must be 3-20 characters long and should contain lowercase and uppercase letters, digits, and underscores only.\nEnter a new username: ");
                                                input = keyboard.next();
                                            }
                                            if(input.equals("stop")) {
                                                stop = true;
                                                continue;
                                            }
                                            else if(input.equals("back")) {
                                                continue;
                                            }
                                            newUsername = input;

                                            u.setUsername(newUsername);
                                            System.out.println("User's username successfully changed. Redirecting to the modify user menu...");
                                        }
                                        else if(input.equals("p")) { // CHANGE USER'S PASSWORD
                                            String newPassword;
                                            System.out.print("Enter a password to replace this user's existing password: ");
                                            input = keyboard.next();
                                            while(!validPassword(input) && !containsBackOrStop(input)) {
                                                System.out.print("The password must be 6-20 characters long and should have at least one lowercase letter, one uppercase letter, one digit, and one special character.\nEnter a password: ");
                                                input = keyboard.next();
                                            }
                                            if(input.equals("stop")) {
                                                stop = true;
                                                continue;
                                            }
                                            else if(input.equals("back")) {
                                                continue;
                                            }
                                            newPassword = input;

                                            u.setPassword(newPassword);
                                            System.out.println("User's password successfully changed. Redirecting to the modify user menu...");
                                        }
                                        else if(input.equals("t")) { // CHANGE USER'S TOKEN
                                            char newToken;
                                            System.out.print("Enter a single-character token to replace this user's existing token: ");
                                            input = keyboard.next();
                                            while(!validToken(input) && !containsBackOrStop(input)) {
                                                System.out.print("The token should be a single uppercase character.\nEnter a token: ");
                                                input = keyboard.next();
                                            }
                                            if(input.equals("stop")) {
                                                stop = true;
                                                continue;
                                            }
                                            else if(input.equals("back")) {
                                                continue;
                                            }
                                            newToken = input.charAt(0);

                                            u.setToken(newToken);
                                            System.out.println("User's token successfully changed. Redirecting to the modify user menu...");
                                        }
                                        else if(input.equals("s")) { // CHANGE USER'S SCORE
                                            int newScore;
                                            System.out.print("Enter a score to replace this user's existing token: ");
                                            input = keyboard.next();
                                            while(!validScore(input) && !containsBackOrStop(input)) {
                                                System.out.print("The score should be an integer greater than or equal to zero.\nEnter a score: ");
                                                input = keyboard.next();
                                            }
                                            if(input.equals("stop")) {
                                                stop = true;
                                                continue;
                                            }
                                            else if(input.equals("back")) {
                                                continue;
                                            }
                                            newScore = Integer.parseInt(input);

                                            u.setScore(newScore);
                                            System.out.println("User's score successfully changed. Redirecting to the modify user menu...");
                                        }
                                    }
                                }
                                else if(input.equals("r")) { // REMOVE USER
                                    boolean invalid2 = true;
                                    String username = null, password = null;
                                    User u = null;
                                    System.out.println("Remove user.");
                                    while(invalid2) {
                                        System.out.print("Enter an existing username: ");
                                        username = keyboard.next();
                                        System.out.print("Enter a password: ");
                                        password = keyboard.next();

                                        if ((u = mainSys.getUser(username, password)) == null) {
                                            System.out.println("User not recognized, try again.");
                                        }
                                        else {
                                            invalid2 = false;
                                        }
                                    }

                                    u = mainSys.getUser(username, password);
                                    mainSys.removeUser(u);
                                    System.out.println("User successfully removed. Redirecting to the user settings menu...");
                                }
                            }
                        }
                        else if(input.equals("l")) { // LOG OUT
                            mainSys.logUserOut(current);
                            loggedIn = false;
                            System.out.println("Successfully logged out. Redirecting to the main menu...");
                        }
                    }
                }
                else {
                    while(loggedIn && !stop) { // USER HOMEPAGE
                        System.out.print(current.getUsername() + "'s homepage.\nPlay (p), view leaderboard (v), send a feedback message (s), change your settings (c), or log out (l)? ");
                        input = keyboard.next();
                        while(!validUserHomepageSelection(input) && !containsBackOrStop(input)) {
                            System.out.print("Please enter 'p', 'v', 's', 'c', or 'l': ");
                            input = keyboard.next();
                        }
                        if(input.equals("stop")) {
                            stop = true;
                        }
                        else if(input.equals("back")) {
                            continue;
                        }

                        if(input.equals("p")) { // PLAY
                            boolean quit = false;
                            while(!quit && !stop) {
                                char token = '\0';

                                if(current.getToken() == '\0') {
                                    System.out.print("Enter a token: ");
                                    input = keyboard.next();
                                    while(!validToken(input) && !containsBackOrStop(input)) {
                                        System.out.print("The token should be a single uppercase character.\nEnter a token: ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }
                                    token = input.charAt(0);
                                }
                                else {
                                    token = current.getToken();
                                }

                                Player p = new Player(current.getUsername(), token);
                                GameResult result = gamemgmt.startGame(p);
                                System.out.println(result.getWinner() + " beat " + result.getLoser() + " with a score of " + result.getScore());

                                System.out.print("Play again (p) or quit (q)? ");
                                input = keyboard.next();
                                while(!validPlayOrQuit(input) && !containsBackOrStop(input)) {
                                    System.out.print("Please enter 'p' or 'q': ");
                                    input = keyboard.next();
                                }
                                if(input.equals("stop")) {
                                    stop = true;
                                }
                                else if(input.equals("q")) {
                                    quit = true;
                                }
                            }
                        }
                        else if(input.equals("v")) { // VIEW LEADERBOARD
                            System.out.println("Leaderboard:\n" + leaderboardSys.displayLeaderboard());
                        }
                        else if(input.equals("s")) { // SEND FEEDBACK MESSAGE
                            System.out.print("Please send your message: ");
                            input = keyboard.next();
                            while(!validFeedbackMsg(input) && !containsBackOrStop(input)) {
                                System.out.print("The message must contain at least one character.\nEnter message: ");
                                input = keyboard.next();
                            }
                            if(input.equals("stop")) {
                                stop = true;
                            }
                            else if(input.equals("back")) {
                                continue;
                            }

                            feedbackSys.receiveMessage(input);
                            System.out.println("Feedback received. Redirecting to your homepage...");
                        }
                        else if(input.equals("c")) { // CHANGE OWN SETTINGS
                            boolean quit = false;
                            while(!quit && !stop) {
                                System.out.print("User profile settings.\nChange username (u), change password (p), or change token (t)? ");
                                input = keyboard.next();
                                while(!validChangeOwnSettingsSelection(input) && !containsBackOrStop(input)) {
                                    System.out.print("Please enter 'u', 'p', or 't': ");
                                    input = keyboard.next();
                                }
                                if(input.equals("stop")) {
                                    stop = true;
                                }
                                else if(input.equals("back")) {
                                    continue;
                                }

                                if(input.equals("u")) { // CHANGE USERNAME
                                    String username;
                                    System.out.print("Enter a new username: ");
                                    // TODO: Check if new username is taken
                                    input = keyboard.next();
                                    while(!validUsername(input) && !containsBackOrStop(input)) {
                                        System.out.print("The username must be 3-20 characters long and should contain lowercase and uppercase letters, digits, and underscores only.\nEnter a new username: ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }
                                    username = input;


                                    current.setUsername(username);
                                    System.out.println("Username successfully changed. Redirecting to your settings menu...");
                                }
                                else if(input.equals("p")) { // CHANGE PASSWORD
                                    System.out.print("Enter your old password: ");
                                    input = keyboard.next();
                                    while(!current.getPassword().equals(input) && !containsBackOrStop(input)) {
                                        System.out.print("Password doesn't match.\nTry again: ");
                                        input = keyboard.next();
                                    }

                                    String pswd1, pswd2;
                                    System.out.print("Enter a new password: ");
                                    input = keyboard.next();
                                    while(!validPassword(input) && !containsBackOrStop(input)) {
                                        System.out.print("The password must be 6-20 characters long and should have at least one lowercase letter, one uppercase letter, one digit, and one special character.\nEnter a new password: ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }
                                    pswd1 = input;

                                    System.out.print("Confirm new password: ");
                                    input = keyboard.next();
                                    while(!pswd1.equals(input)) {
                                        System.out.print("Password doesn't match.\nTry again: ");
                                        input = keyboard.next();
                                    }
                                    pswd2 = input;

                                    current.setPassword(pswd2);
                                    System.out.println("Password successfully changed. Redirecting to your settings menu...");
                                }
                                else if(input.equals("t")) { // CHANGE TOKEN
                                    char token;
                                    System.out.print("Enter a new token: ");
                                    input = keyboard.next();
                                    while(!validToken(input) && !containsBackOrStop(input)) {
                                        System.out.print("The token should be a single uppercase character.\nEnter a token: ");
                                        input = keyboard.next();
                                    }
                                    if(input.equals("stop")) {
                                        stop = true;
                                        continue;
                                    }
                                    else if(input.equals("back")) {
                                        continue;
                                    }
                                    token = input.charAt(0);

                                    current.setToken(token);
                                    System.out.println("Token successfully changed. Redirecting to your settings menu...");
                                }
                            }
                        }
                        else if(input.equals("l")) { // LOG OUT
                            mainSys.logUserOut(current);
                            loggedIn = false;
                            System.out.println("Successfully logged out. Redirecting to the main menu...");
                        }
                    }
                }
            }
            else if(input.equals("r")) { // REGISTER
                String username, password;
                System.out.print("Enter a username: ");
                input = keyboard.next();
                while(!validUsername(input) && !containsBackOrStop(input)) {
                    System.out.print("The username must be 3-20 characters long and should contain lowercase and uppercase letters, digits, and underscores only.\nEnter a username: ");
                    input = keyboard.next();
                }
                if(input.equals("stop")) {
                    stop = true;
                    continue;
                }
                else if(input.equals("back")) {
                    continue;
                }
                username = input;

                System.out.print("Enter a password: ");
                input = keyboard.next();
                while(!validPassword(input) && !containsBackOrStop(input)) {
                    System.out.print("The password must be 6-20 characters long and should have at least one lowercase letter, one uppercase letter, one digit, and one special character.\nEnter a password: ");
                    input = keyboard.next();
                }
                if(input.equals("stop")) {
                    stop = true;
                    continue;
                }
                else if(input.equals("back")) {
                    continue;
                }
                password = input;

                User u = new User(username, password);
                mainSys.addUser(u);
                System.out.println("Registration successful. Redirecting to the main page...");
            }
            else if(input.equals("p")) { // PLAY WHILE SIGNED OUT
                boolean quit = false;
                while(!quit && !stop) {
                    String name;
                    char token;
                    System.out.print("Please enter a name: ");
                    input = keyboard.next();
                    while(!validName(input) && !containsBackOrStop(input)) {
                        System.out.print("The name must be 3-20 characters long and should contain lowercase and uppercase letters, digits, and underscores only.\nEnter a name: ");
                        input = keyboard.next();
                    }
                    if(input.equals("stop")) {
                        stop = true;
                        continue;
                    }
                    else if(input.equals("back")) {
                        break;
                    }
                    name = input;

                    System.out.print("Enter a token: ");
                    input = keyboard.next();
                    while(!validToken(input) && !containsBackOrStop(input)) {
                        System.out.print("The token should be a single uppercase character.\nEnter a token: ");
                        input = keyboard.next();
                    }
                    if(input.equals("stop")) {
                        stop = true;
                        continue;
                    }
                    else if(input.equals("back")) {
                        continue;
                    }
                    token = input.charAt(0);

                    Player p = new Player(name, token);
                    GameResult result = gamemgmt.startGame(p);

                    System.out.println(result.getWinner() + " beat " + result.getLoser() + " with a score of " + result.getScore());
                    System.out.print("Play again (p) or quit (q)? ");
                    input = keyboard.next();
                    while(!validPlayOrQuit(input) && !containsBackOrStop(input)) {
                        System.out.print("Please enter 'p' or 'q': ");
                        input = keyboard.next();
                    }
                    if(input.equals("stop")) {
                        stop = true;
                    }
                    else if(input.equals("q")) {
                        quit = true;
                    }
                }
            }
            else if(input.equals("v")) { // VIEW LEADERBOARD
                System.out.println("Leaderboard:\n" + leaderboardSys.displayLeaderboard());
            }
        }
        System.out.println("Thank you for playing Connect 4.");
    }

    public static boolean containsBackOrStop(String str) {
        return str.equals("back") || str.equals("stop");
    }
    public static boolean validUsername(String username) {
        return username != null && username.matches("^(?=.{2,20})[a-zA-Z0-9_]*$");
    }
    public static boolean validPassword(String password) {
        return password != null && password.matches("^(?=.{6,20})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[-!@#$%^&*+=_]).*$");
    }
    public static boolean validScore(String score) {
        return score.matches("\\d+") && Integer.parseInt(score) >= 0;
    }
    public static boolean validToken(String token) {
        return token.length() == 1 && isUpperCase(token.charAt(0));
    }
    public static boolean validName(String name) { // For playing without an account
        return name != null && name.matches("^(?=.{3,20})[a-zA-Z0-9_]*$");
    }
    public static boolean validFeedbackMsg(String msg) {
        return msg.length() >= 1;
    }
    public static boolean validPlayOrQuit(String choice) {
        return choice.length() == 1 && choice.charAt(0) == 'p' || choice.charAt(0) == 'q';
    }
    public static boolean validYesOrNo(String choice) {
        return choice.length() == 1 && choice.charAt(0) == 'y' || choice.charAt(0) == 'n';
    }
    public static boolean validMainMenuSelection(String selection) {
        return selection.length() == 1 && selection.charAt(0) == 's' || selection.charAt(0) == 'r' || selection.charAt(0) == 'p' || selection.charAt(0) == 'v';
    }
    public static boolean validUserHomepageSelection(String selection) {
        return selection.length() == 1 && selection.charAt(0) == 'p' || selection.charAt(0) == 'v' || selection.charAt(0) == 's' || selection.charAt(0) == 'c' || selection.charAt(0) == 'l';
    }
    public static boolean validChangeOwnSettingsSelection(String selection) {
        return selection.length() == 1 && selection.charAt(0) == 'u' || selection.charAt(0) == 'p' || selection.charAt(0) == 't';
    }
    public static boolean validAdminHomepageSelection(String selection) {
        return selection.length() <= 2 && (selection.equals("p") || selection.equals("vl") || selection.equals("c") || selection.equals("vd") || selection.equals("a") || selection.equals("l"));
    }
    public static boolean validAllUserSettingsSelection(String selection) {
        return selection.length() == 1 && selection.charAt(0) == 'a' || selection.charAt(0) == 'c' || selection.charAt(0) == 'r';
    }
    public static boolean validChangeUserAttributesSelection(String selection) {
        return selection.length() == 1 && selection.charAt(0) == 'u' || selection.charAt(0) == 'p' || selection.charAt(0) == 't' || selection.charAt(0) == 's';
    }
}
