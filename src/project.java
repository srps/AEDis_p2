import SuffixTrees.SuffixTree;

import java.io.BufferedReader;
import java.io.IOException;
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

                if ((r >= 'A' && r <= 'Z') || (r >= 'a' && r <= '<') || r == '$') {
                    input.add((char) r);
                    if (input.get(input.size() - 1) == '$') {
                        if (input.get(0) == '$') {
                            break;
                        }
                        input.remove(input.size() - 1);
                        //ST = new SuffixTree(input); TODO : implement SuffixTree
                        input.clear();
                        System.out.println(input.size());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
