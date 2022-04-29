import java.util.ArrayList;

public class LeaderboardSys {
    private ArrayList<User> leaderboard;

    public LeaderboardSys() { leaderboard = new ArrayList<User>(); }
    public LeaderboardSys(ArrayList<User> users) {
        leaderboard = new ArrayList<User>();
        leaderboard.addAll(users);
    }

    public void addUser(User u) { leaderboard.add(u); }
    public void removeUser(User u) { leaderboard.remove(u); }
    public void update(User u, int score) { leaderboard.get(leaderboard.indexOf(u)).update(score); }

    public ArrayList<User> getLeaderboard() {
        leaderboard.sort(new UserComparator().reversed());
        return leaderboard;
    }

    public String displayLeaderboard() {
        leaderboard.sort(new UserComparator().reversed());
        String output = "";
        for(User u : leaderboard) {
            output += u.getUsername() + " -- " + u.getScore() + "\n";
        }
        return output;
    }
}
