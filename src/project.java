import SuffixTrees.SuffixTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.BitSet;

/**
 * User: Sergio Silva   - 55457
 * User: Marco Ferreira - 56886
 * Date: 10-12-2012
 * Time: 15:25
 */

public class project {

    public static void main(String[] args) {

        long startTime = System.nanoTime();
        int r;
        BitSet capitals;

        SuffixTree ST;
        ArrayList<Character> input = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
        }

        try (
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(new StringReader(sb.toString()))) {

            while ((r = in.read()) != -1) {

                if ((r >= 'A' && r <= 'Z') || (r >= 'a' && r <= 'z') || r == '$') {
                    input.add((char) r);
                    if (input.get(input.size() - 1) == '$') {
                        if (input.get(0) == '$') {
                            break;
                        }
                        input.remove(input.size() - 1);                             // remove the money
                        input.addAll(input);                                        // duplicate the input
                        ST = new SuffixTree(input.size());                          // create the stub tree
                        capitals = new BitSet(input.size());
                        int setIndex = 0;
                        for (Character c : input) {
                            ST.insertCharTree(c);                                          // build char by char
                            if (c <= 'Z') {
                                capitals.set(setIndex);
                            }
                            setIndex++;
                        }
                        ST.printOrderedString(ST.getRoot(), input.size() / 2 - 1, capitals);
                        System.out.println();
                        input.clear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Execution time: " + (System.nanoTime() - startTime) / 1000000.0 + "ms");
    }
}
