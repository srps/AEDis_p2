import SuffixTrees.SuffixTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;

/**
 * User: Sergio Silva   - 55457
 * User: Marco Ferreira - 56886
 * Date: 10-12-2012
 * Time: 15:25
 */

public class project {

    public static void main(String... args) {

        int r;                                                                      // aux for reading
        BitSet capitals;                                                            // capital checker

        SuffixTree ST;
        ArrayList<Character> input = new ArrayList<>();                             // to keep the sentences

        try (
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(isr)) {

            while ((r = in.read()) != -1) {                                         // while there's input to be read

                if ((r >= 'A' && r <= 'Z') || (r >= 'a' && r <= 'z') || r == '$') { // only get valid characters
                    input.add((char) r);
                    if (input.get(input.size() - 1) == '$') {                       // sentence or input end reached
                        if (input.get(0) == '$') {                                  // if we catch a second dollar
                            break;                                                  // we reached input end
                        }
                        input.remove(input.size() - 1);                             // remove the money
                        input.addAll(input);                                        // duplicate the input
                        ST = new SuffixTree(input.size());                          // create the stub tree
                        capitals = new BitSet(input.size());
                        int setIndex = 0;                                           // setting BitSet index
                        for (Character c : input) {
                            ST.insertCharTree(c);                                   // build char by char
                            if (c <= 'Z') {                                         // if it's capital
                                capitals.set(setIndex);                             // remind the printer so
                            }
                            setIndex++;
                        }
                        ST.printOrderedString(ST.getRoot(), input.size() / 2 - 1, capitals); // print results
                        System.out.println();                                                // and a newline
                        input.clear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
