package SuffixTrees;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sergio
 * Date: 10-12-2012
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class Node<I extends Character, S extends Iterable<I>> implements Iterable<Edge<I, S>> {

    private final Map<I, Edge<I, S>> edges = new HashMap<>();
    private final Edge<I, S> parentEdge;
    private final List<Character> seq;
    private final SuffixTree<I, S> sTree;
    private Node<I, S> sLink = null;

    protected Node(Edge<I, S> parentEdge, List<Character> sequence, SuffixTree<I, S> suffixTree) {
        this.parentEdge = parentEdge;
        this.seq = sequence;
        this.sTree = suffixTree;
    }

    public Iterator<Edge<I, S>> iterator() {
        return edges.values().iterator();
    }

}
