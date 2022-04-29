import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegisterUser_UseCase_Testing {
    @Test
    public void registerUser_TC1() {
        Assertions.assertEquals(RegisterUser.registerUser("a", "abcdA1!"), "Username length is outside of the valid range.");
    }

    @Test
    public void registerUser_TC2() {
        Assertions.assertEquals(RegisterUser.registerUser("ab", ""), "Password length is outside of the valid range.");
    }

    @Test
    public void registerUser_TC3() {
        Assertions.assertEquals(RegisterUser.registerUser("!@", "abcdA1!"), "Username contents are invalid.");
    }

    @Test
    public void registerUser_TC4() {
        Assertions.assertEquals(RegisterUser.registerUser("ab", "abcdefg"), "Password contents are invalid.");
    }

    @Test
    public void registerUser_TC5() {
        Assertions.assertEquals(RegisterUser.registerUser("ab", "abcdA1!"), "Username and password are valid.");
    }
}
