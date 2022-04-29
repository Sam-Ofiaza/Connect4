import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SendFeedback_UseCase_Testing {
    @Test
    public void sendFeedback_TC1() {
        Assertions.assertEquals(SendFeedback.sendFeedback("a"),"Message is too short.");
    }

    @Test
    public void sendFeedback_TC2() {
        Assertions.assertEquals(SendFeedback.sendFeedback(("Hi")), "Message is valid.");
    }
}
