package SuffixTrees;

/**
 * User: Sergio Silva   - 55457
 * User: Marco Ferreira - 56886
 * Date: 10-12-2012
 * Time: 15:25
 */

public class Node {

    final int oo = Integer.MAX_VALUE / 2;
    final int ALPHABET_SIZE = 26;

    private SuffixTree tree;
    int start = -1,
            end = oo,
            link = -1;
    int[] next = new int[ALPHABET_SIZE];

    public Node(int start, SuffixTree tree) {
        this.start = start;
        this.tree = tree;
    }

    public Node(int start, int end, SuffixTree tree) {
        this.start = start;
        this.end = end;
        this.tree = tree;
    }

    public int edgeLength() {
        return Math.min(end, tree.position + 1) - start;
    }
}