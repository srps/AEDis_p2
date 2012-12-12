package SuffixTrees;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Sergio
 * Date: 11-12-2012
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
public class Suffix<I extends Character, S extends Iterable<I>> {

    private int start;
    private int end;
    private List<Character> seq;

    public Suffix(int start, int end, List<Character> sequence) {
        this.start = start;
        this.end = end;
        this.seq = sequence;
    }

}
