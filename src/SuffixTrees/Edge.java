package SuffixTrees;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Sergio
 * Date: 10-12-2012
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */
public class Edge<I extends Character, S extends Iterable<I>> implements Iterable<I> {

    private final int start;
    private int end = -1;
    private final Node<I, S> parentNode;
    private final String sequence;

    private Node<I, S> terminal = null;
    private SuffixTree<I, S> sTree = null;

    protected Edge(int start, Node<I, S> parent, String sequence, SuffixTree<I, S> sTree) {
        this.start = start;
        this.parentNode = parent;
        this.sequence = sequence;
        this.sTree = sTree;
    }

    int getEnd() {
        sTree.getCurrentEnd();
        return end != -1 ? end : sTree.getCurrentEnd();
    }

    public Iterator<I> iterator() {
        return new Iterator<I>() {
            private int currentPosition = start;
            private boolean hasNext = true;

            public boolean hasNext() {
                return hasNext;
            }

            @SuppressWarnings("unchecked")
            public I next() {
                if (end == -1)
                    hasNext = sequence.charAt(currentPosition) == '$';
                else
                    hasNext = currentPosition < getEnd() - 1;
                return (I) Character.valueOf(sequence.charAt(currentPosition++));
            }

            public void remove() {
                throw new UnsupportedOperationException(
                        "The remove method is not supported.");
            }
        };
    }

}
