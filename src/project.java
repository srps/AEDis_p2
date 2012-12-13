import SuffixTrees.SuffixTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class project {

    public static void main(String[] args) {

        SuffixTree ST;
        int r;
        ArrayList<Character> input = new ArrayList<>();

        try (
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(isr)) {

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
                        for (Character c : input) {
                            ST.addChar(c);                                          // build char by char
                        }
                        System.out.println(ST.countDistinctSubstrings(ST.getRoot()));
                        ST.printDistinctSubstrings(ST.getRoot());
                        input.clear();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
