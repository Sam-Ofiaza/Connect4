import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

public class FeedbackSys {
    private Queue<String> messages;

    public FeedbackSys() { messages = new LinkedList<String>(); }

    public void readExistingMessages(String pathname) {
        try {
            File file = new File(pathname);
            file.createNewFile(); // Creates a file if one doesn't already exist
            Scanner textInput = new Scanner(file);
            while(textInput.hasNextLine()) {
                messages.add(textInput.nextLine());
            }
            textInput.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("File at path " + pathname + " not found.");
            e.printStackTrace();
        }
        catch(IOException e) {
            System.out.println("An error occurred while trying to create " + pathname + ".");
        }
    }

    public void saveCurrentMessages(String pathname) {
        try {
            File file = new File(pathname);
            file.createNewFile(); // Creates a file if one doesn't already exist
            FileWriter writer = new FileWriter(pathname);

            for(String s : messages) {
                writer.write(s + "\n");
            }
            writer.close();
        }
        catch(IOException e) {
            System.out.println("An error occurred while trying to create or write to " + pathname + ".");
        }
    }

    public void receiveMessage(String msg) { messages.add(msg); }

    public String viewMessages() {
        String output = "";
        for(String s : messages)
            output += s + "\n";
        return output;
    }
}
