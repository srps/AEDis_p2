import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

public class project {

    public static void main(String[] args) {

        boolean finishedInput = false;
        boolean finishedString = false;
        String[] input;
        String curLine;
        LinkedList<String> fifo = new LinkedList<>();
        StringBuilder builder = new StringBuilder();

        try (
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(isr)) {

            // While $$ sequence isn't seen
            while (!finishedInput || !finishedString) {
                // Checks if there's previously seen input to process
                if (!fifo.isEmpty()) {
                    // Gets first unprocessed string and appends it to builder
                    builder.append(fifo.removeFirst());
                    // Checks if there are potentially still unread characters in the current input
                    while (fifo.isEmpty()) {
                        // If so, go get them to process
                        curLine = in.readLine();
                        if (curLine.contains("$")) {
                            finishedString = true;
                            finishedInput = curLine.contains("$$");
                            input = curLine.split("\\$");
                            fifo.addAll(Arrays.asList(input));

                        } else {
                            fifo.add(curLine);
                        }
                        if (!fifo.isEmpty()) {
                            builder.append(fifo.removeFirst());
                        }
                    }
                } else {
                    curLine = in.readLine();
                    if (curLine.contains("$")) {
                        finishedString = true;
                        finishedInput = curLine.contains("$$");
                        input = curLine.split("\\$");
                        fifo.addAll(Arrays.asList(input));

                    } else {
                        fifo.add(curLine);
                    }
                    if (!fifo.isEmpty()) {
                        builder.append(fifo.removeFirst());
                    }
                }
                if (finishedString) {
                    // Testing purposes only TODO : replace with SuffixTree call
                    System.out.println(builder.toString());
                    builder.setLength(0);
                    finishedString = finishedInput;
                }
            }

        } catch (IOException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
