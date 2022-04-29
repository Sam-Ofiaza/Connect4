public class SendFeedback {
    public static final int MIN_MSG_LEN = 2;

    public static boolean checkMsgLength(String message) {
        return message.length() < MIN_MSG_LEN;
    }

    public static String sendFeedback(String message) {
        if(checkMsgLength(message)) {
            return "Message is too short.";
        }
        return "Message is valid.";
    }
}
