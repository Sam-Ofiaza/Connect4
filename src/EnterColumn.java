public class EnterColumn {
    public static final int MIN_COL = 1;
    public static final int MAX_COL = 7;

    public static boolean checkOutsideRange(int col) {
        if(col < 1 || col > 7) {
            return true;
        }
        return false;
    }

    public static boolean checkColFull(boolean[] cols, int col) {
        return cols[col-1];
    }

    public static String enterColumn(int col, boolean[] cols) {
        if(checkOutsideRange(col)) {
            return "Column is outside of the valid range.";
        }
        if(checkColFull(cols, col)) {
            return "Column is already full.";
        }
        return "Column is valid.";
    }
}
