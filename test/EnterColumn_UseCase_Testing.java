import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnterColumn_UseCase_Testing {
    @Test
    public void enterColumn_TC1() {
        boolean[] cols = {false, false, false, false, false, false, false};
        Assertions.assertEquals(EnterColumn.enterColumn(0, cols), "Column is outside of the valid range.");
    }

    @Test
    public void enterColumn_TC2() {
        boolean[] cols = {false, false, false, false, false, false, false};
        Assertions.assertEquals(EnterColumn.enterColumn(8, cols), "Column is outside of the valid range.");
    }

    @Test
    public void enterColumn_TC3() {
        boolean[] cols = {true, false, false, false, false, false, false};
        Assertions.assertEquals(EnterColumn.enterColumn(1, cols), "Column is already full.");
    }

    @Test
    public void enterColumn_TC4() {
        boolean[] cols = {true, false, false, false, false, false, false};
        Assertions.assertEquals(EnterColumn.enterColumn(2, cols), "Column is valid.");
    }
}
