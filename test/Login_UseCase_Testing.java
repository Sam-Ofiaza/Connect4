import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Login_UseCase_Testing {
    @Test
    public void login_TC1() {
        Assertions.assertEquals(LogUserIn.login("cherry", "yrrehc"), "Username not recognized.");
    }

    @Test
    public void login_TC2() {
        Assertions.assertEquals(LogUserIn.login("banana", "anana"), "Incorrect password.");
    }

    @Test
    public void login_TC3() {
        Assertions.assertEquals(LogUserIn.login("apple", "elppa"), "Login successful.");
    }
}
