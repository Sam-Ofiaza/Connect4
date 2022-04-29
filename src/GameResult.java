public class GameResult {
    private Player winner, loser;
    private int score;

    public GameResult(Player winner, Player loser, int score) { this.winner = winner; this.loser = loser; this.score = score; }

    public Player getWinner() { return winner; }
    public Player getLoser() { return loser; }
    public int getScore() { return score; }

    public String toString() { return winner.getName(); }
}
