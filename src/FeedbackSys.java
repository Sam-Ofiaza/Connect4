import java.util.Queue;
import java.util.LinkedList;

public class FeedbackSys {
    private Queue<String> messages;

    public FeedbackSys() { messages = new LinkedList<String>(); }

    public void receiveMessage(String msg) { messages.add(msg); }

    public String viewMessages() {
        String output = "";
        for(String s : messages)
            output += s + "\n";
        return output;
    }
}
