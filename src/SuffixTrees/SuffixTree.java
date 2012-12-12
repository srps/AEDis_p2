package SuffixTrees;

/**
 * Created with IntelliJ IDEA.
 * User: Sergio
 * Date: 10-12-2012
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class SuffixTree<I extends Character, S extends Iterable<I>> {

    private final Node<I, S> root;
    private final String seq;
    private final ActivePoint<I, S> aPoint;

    private Node<I, S> lastNode;
    private Suffix<I, S> suffix;

    private int remainder;
    private int currentEnd;


    public SuffixTree(String S) {

        this.seq = S;
        this.root = new Node<I, S>(null, this.seq, this);
        this.aPoint = new ActivePoint<I, S>(root);

        createTree(S.length());

    }

    private void createTree(int length) {

    }

    int getCurrentEnd() {
        return currentEnd;
    }

}
