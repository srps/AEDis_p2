package SuffixTrees;

/**
 * Created with IntelliJ IDEA.
 * User: Sergio
 * Date: 10-12-2012
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
public class ActivePoint {

    // TODO : repair ActivePoint logically, take illogical vars out
    protected int root;             // repor em SuffixTree
    protected int pseudoRoot;       // repor em SuffixTree
    protected Node[] nodes;         // repor em SuffixTree
    protected char[] edges;         // repor em SuffixTree e renomear para charMap
    protected int activeNode = 0;   // mudar para Node aNode
    protected int activeEdge = 0;   // mudar para char aEdge
    protected int activeLength = 0;

    protected ActivePoint(Node root, char activeEdge) {
        //this.aNode = root;
        //this.aEdge = activeEdge;
        this.activeLength = 0;
    }

    protected ActivePoint() {
    }


}
