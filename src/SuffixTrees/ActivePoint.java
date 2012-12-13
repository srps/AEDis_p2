package SuffixTrees;

/**
 * Created with IntelliJ IDEA.
 * User: Sergio
 * Date: 10-12-2012
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class ActivePoint {

    protected int root;
    protected int pseudoRoot;
    protected Node[] nodes;
    protected char[] edges;
    protected int activeNode = 0;
    protected int activeEdge = 0;
    protected int activeLength = 0;

    protected ActivePoint(Node[] nodes, char[] edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.activeLength = 0;
    }

    protected ActivePoint() {
    }


}
