package SuffixTrees;

/**
 * User: Sergio Silva   - 55457
 * User: Marco Ferreira - 56886
 * Date: 10-12-2012
 * Time: 15:25
 */

public class Node {

    private final int oo = Integer.MAX_VALUE / 2;
    private final int ALPHABET_SIZE = 26;

    /**
     * the [start,end] interval sets the edge
     * charMap[node.start]...charMap[node.end - 1] - start is inclusive and end is exclusive
     * link sets the suffix linked edge pointer
     */

    private final SuffixTree tree;
    int start = -1,
            end = oo,
            link = -1;
    final int[] next = new int[ALPHABET_SIZE];

    /**
     * @param start - start of the edge pointer
     * @param tree  - the suffix tree associated with the node
     */
    public Node(int start, SuffixTree tree) {
        this.start = start;
        this.tree = tree;
    }

    /**
     * @param start - start of the edge pointer
     * @param end   - end of the edge pointer
     * @param tree  - the suffix tree associated with the node
     */
    public Node(int start, int end, SuffixTree tree) {
        this.start = start;
        this.end = end;
        this.tree = tree;
    }

    /**
     * @return - the true edgeLength value
     */
    public int edgeLength() {
        return Math.min(end, tree.position + 1) - start;
    }
}