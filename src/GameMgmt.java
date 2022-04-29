import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class GameMgmt {
    private char[][] board;
    private char defToken, compToken;
    private Player computer;
    private boolean[] fullCols;

    public GameMgmt() {
        defToken = '-';
        compToken = 'C';
        computer = new Player("Computer", compToken);

        fullCols = new boolean[7];
        Arrays.fill(fullCols, false);

        board = new char[6][7];
        for(char[] row : board) {
            Arrays.fill(row, defToken);
        }
    }

    public void resetBoard() {
        for(char[] row : board) {
            Arrays.fill(row, defToken);
        }
    }

    // TODO: Optimize (currently uses brute force - checks every possible way to win)
    public boolean checkGameOver(char token) {
        // Check for a horizontal
        //System.out.print("Checking horizontals...");
        for(int r = 0; r < 6; r++) {
            for(int c = 0; c < 4; c++) {
                //System.out.print(" (" + r + ", " + c + ") ");
                if(board[r][c] == token && board[r][c + 1] == token && board[r][c + 2] == token && board[r][c + 3] == token) {
                    return true;
                }
            }
        }

        // Check for a vertical
        //System.out.print("\nChecking verticals...");
        for(int c = 0; c < 7; c++) {
            for(int r = 0; r < 3; r++) {
                //System.out.print(" (" + r + ", " + c + ") ");
                if(board[r][c] == token && board[r + 1][c] == token && board[r + 2][c] == token && board[r + 3][c] == token) {
                    return true;
                }
            }
        }

        // Check for a left diagonal ( \ )
        //System.out.print("\nChecking left diagonals ( \\ )...");
        for(int r = 0; r < 3; r++) {
            for(int c = 0; c < 4; c++) {
                //System.out.print(" (" + r + ", " + c + ") ");
                if(board[r][c] == token && board[r + 1][c + 1] == token && board[r + 2][c + 2] == token && board[r + 3][c + 3] == token) {
                    return true;
                }
            }
        }

        // Check for a right diagonal ( / )
        //System.out.print("\nChecking right diagonals ( / )...");
        for(int r = 3; r < 6; r++) {
            for(int c = 0; c < 4; c++) {
                //System.out.print(" (" + r + ", " + c + ") ");
                if(board[r][c] == token && board[r - 1][c + 1] == token && board[r - 2][c + 2] == token && board[r - 3][c + 3] == token) {
                    return true;
                }
            }
        }

        //System.out.println();

        return false;
    }

    public boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public GameResult startGame(Player p) {
        resetBoard();
        int turns = 21;
        /*
            Maximum number of moves in Connect 4 = 42. 42 / 2 = 21 possible turns before the board is full.
            The difference between the number of turns between p1 and p2 will never be more than 2.
            If score is only decremented when p1 goes, then if p1 wins, p1's score will be turns.
            If p2 wins, p2's score will be turns + 1
        */

        System.out.println(p.getName() + " vs Computer\n");
        System.out.println(this);

        Player winner = null;
        while(winner == null) {
            Scanner input = new Scanner(System.in);
            int col;

            // Player places a token
            System.out.print(p.getName() + ", enter a column (1-7): ");
            String entry = input.next();

            // Validate input
            boolean flag1 = !isNumeric(entry), flag2 = false, flag3 = false;
            if(!flag1) {
                int num = Integer.parseInt(entry);
                flag2 = num < 0 || num > 7;
                if(!flag2)
                    flag3 = fullCols[num-1];
            }
            while(flag1 || flag2 || flag3) {
                if(flag1) {
                    System.out.print("Please enter a number: ");
                }
                else if(flag2) {
                    System.out.print("Please enter a number from 1 to 7: ");
                }
                else if(flag3) {
                    System.out.print("Column is full - please enter another column: ");
                }
                entry = input.next();
                flag1 = !isNumeric(entry);
                if(!flag1) {
                    int num = Integer.parseInt(entry);
                    flag2 = num < 0 || num > 7;
                    if(!flag2)
                        flag3 = fullCols[num-1];
                }
            }
            col = Integer.parseInt(entry) - 1;
            for(int r = 5; r >= 0; r--) {
                if(board[r][col] == '-') {
                    board[r][col] = p.getToken();
                    if(r == 0) {
                        fullCols[col] = true;
                    }
                    break;
                }
            }
            turns--;

            if(checkGameOver(p.getToken())) {
                winner = p;
                break;
            }

            // Computer places a token
            // TODO: Create smart AI opponent (currently places token randomly)
            col = ThreadLocalRandom.current().nextInt(1, 8) - 1;
            System.out.println("Computer picked " + (col+1) + ".\n");
            for(int r = 5; r >= 0; r--) {
                if(board[r][col] == '-') {
                    board[r][col] = computer.getToken();
                    if(r == 0) {
                        fullCols[col] = true;
                    }
                    break;
                }
            }

            if(checkGameOver(computer.getToken())) {
                winner = computer;
                break;
            }

            // Display board
            System.out.println(toString());
        }

        // Return result
        if(winner == p)
            return new GameResult(p, computer, turns);
        else
            return new GameResult(computer, p, turns+1);
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for(char[] row : board) {
            output.append(Arrays.toString(row)).append("\n");
        }
        return output.toString();
    }
}
