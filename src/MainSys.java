import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainSys {
    private ArrayList<User> users;
    private ArrayList<User> usersLoggedIn;

    public MainSys() {
        users = new ArrayList<User>();
        usersLoggedIn = new ArrayList<User>();
    }

    public void readExistingData(String pathname) {
        try {
            File file = new File(pathname);
            file.createNewFile(); // Creates a file if one doesn't already exist
            Scanner textInput = new Scanner(file);
            while(textInput.hasNextLine()) {
                Scanner lineReader = new Scanner(textInput.nextLine());
                while(lineReader.hasNext()) {
                    String username, password;
                    char token;
                    int score;

                    username = lineReader.next();
                    password = lineReader.next();
                    score = Integer.parseInt(lineReader.next());
                    token = lineReader.next().charAt(0);

                    User u = new User(username, password, score, token);
                    if(!users.contains(u)) // Compares username-password pairs
                        users.add(u);
                    else {
                        User u2 = users.get(users.indexOf(u));
                        u2.setScore(score);
                        u2.setToken(token);
                    }
                }

            }
            textInput.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("File at path " + pathname + " not found.");
            e.printStackTrace();
        }
        catch(IOException e) {
            System.out.println("An error occurred while trying to create " + pathname + ".");
        }
    }

    public void saveCurrentData(String pathname) {
        try {
            File file = new File(pathname);
            file.createNewFile(); // Creates a file if one doesn't already exist
            FileWriter writer = new FileWriter(pathname);

            for(User u : users) {
                writer.write(u.getUsername() + " " + u.getPassword() + " " + u.getScore() + " " + u.getToken() + "\n");
            }
            writer.close();
        }
        catch(IOException e) {
            System.out.println("An error occurred while trying to create or write to " + pathname + ".");
        }
    }

    public void addUser(User u) { users.add(u); }

    public void removeUser(User u) {
        usersLoggedIn.remove(u);
        users.remove(u);
    }

    public boolean logUserIn(String user, String pswd) {
        for(User u : users)
            if(u.getUsername().equals(user) && u.getPassword().equals(pswd)) {
                usersLoggedIn.add(u);
                return true;
            }
        return false;
    }
    public void logUserOut(User u) {
        usersLoggedIn.remove(u);
    }
    public void logAllUsersOut() {
        for(User u : usersLoggedIn)
            usersLoggedIn.remove(u);
    }

    public User getUser(String user, String pswd) {
        for(User u : users)
            if(u.getUsername().equals(user) && u.getPassword().equals(pswd))
                return u;
        return null;
    }
    public ArrayList<User> getUsers(){
        return users;
    }
    public ArrayList<User> getUsersLoggedIn() {
        return usersLoggedIn;
    }

    // For debugging
    public String displayData() {
        String output = "";
        for(User u : users) {
            output += u.getUsername() + " " + u.getPassword() + " " + u.getScore() + " " + u.getToken() + "\n";
        }
        return output;
    }

    public String displayLoggedInUsers() {
        String output = "Users logged in: ";
        for(User u : usersLoggedIn) {
            output += u.getUsername() + " ";
        }
        return output;
    }
}
