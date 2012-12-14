package SuffixTrees;

/**
 * User: Sergio Silva   - 55457
 * User: Marco Ferreira - 56886
 * Date: 10-12-2012
 * Time: 15:25
 */

public class ActivePoint {

    // TODO : repair ActivePoint logically, take illogical vars out
    private int aNodePointer = 0;   // mudar para Node aNode
    private int aEdgePointer = 0;   // mudar para char aEdge
    private int activeLength = 0;
    private SuffixTree suffixTree;

    protected ActivePoint(SuffixTree sTree) {
        this.suffixTree = sTree;
    }

    protected void setNodePointer(int value) {
        aNodePointer = value;
    }

    protected void setEdgePointer(int value) {
        aEdgePointer = value;
    }

    protected void setActiveLength(int value) {
        activeLength = value;
    }

    protected int getNodePointer() {
        return aNodePointer;
    }

    protected int getEdgePointer() {
        return aEdgePointer;
    }

    protected int getActiveLength() {
        return activeLength;
    }

    protected char getActiveEdge() {
        return suffixTree.edgeMap[aEdgePointer];
    }


}
