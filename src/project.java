import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class project {

    public static void main(String[] args) {

        boolean finishedInput = false;
        boolean finishedSentence = false;
        int stringCounter = 0;
        String[] input;
        String curLine;
        LinkedList<String> fifo = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        Iterator<String> it;

        try (
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(isr)) {

            // While $$ sequence isn't seen
            while (!finishedInput || !finishedSentence || stringCounter > 0) {
                // Checks if there's previously seen input to process
                if (!fifo.isEmpty()) {
                    // Gets first unprocessed string and appends it to builder
                    builder.append(fifo.removeFirst());
                    stringCounter--;
                    finishedSentence = !fifo.isEmpty();
                    // Checks if there are potentially still unread characters in the current input
                    while (fifo.isEmpty()) {
                        // If so, go get them to process
                        curLine = in.readLine();
                        if (curLine.contains("$")) {
                            stringCounter += curLine.split("\\$").length;
                            System.out.println("stringCounter: " + stringCounter);
                            finishedInput = curLine.contains("$$");
                            input = curLine.split("\\$");
                            fifo.addAll(Arrays.asList(input));
                            it = fifo.iterator();
                            while (it.hasNext()) {
                                System.out.println(it.next());
                            }
                        } else {
                            fifo.add(curLine);
                        }
                        if (!fifo.isEmpty()) {
                            builder.append(fifo.removeFirst());
                            stringCounter--;
                            finishedSentence = !fifo.isEmpty();
                            break;
                        }
                        System.out.println("current line: " + curLine);
                        System.out.println(builder.toString());
                    }
                } else {
                    curLine = in.readLine();
                    if (curLine.contains("$")) {
                        stringCounter += curLine.split("\\$").length;
                        System.out.println("stringCounter: " + stringCounter);
                        finishedInput = curLine.contains("$$");
                        input = curLine.split("\\$");
                        fifo.addAll(Arrays.asList(input));

                    } else {
                        fifo.add(curLine);
                    }
                    if (!fifo.isEmpty()) {
                        builder.append(fifo.removeFirst());
                        stringCounter--;
                        finishedSentence = !fifo.isEmpty();
                    }
                }
                if (finishedSentence) {
                    // Testing purposes only TODO : replace with SuffixTree call
                    System.out.println(builder.toString());
                    builder.setLength(0);
                }
            }

        } catch (IOException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
