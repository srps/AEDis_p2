package SuffixTrees;

/**
 * User: Sergio Silva   - 55457
 * User: Marco Ferreira - 56886
 * Date: 10-12-2012
 * Time: 15:25
 */

class ActivePoint {

    private int aNodePointer = 0;           // activeNode pointer
    private int aEdgePointer = 0;           // activeEdge pointer
    private int activeLength = 0;           // activeLength
    private final SuffixTree suffixTree;    // this suffix tree associated

    ActivePoint(SuffixTree sTree) {
        this.suffixTree = sTree;
    }

    void setNodePointer(int value) {
        aNodePointer = value;
    }

    void setEdgePointer(int value) {
        aEdgePointer = value;
    }

    void setActiveLength(int value) {
        activeLength = value;
    }

    int getNodePointer() {
        return aNodePointer;
    }

    int getEdgePointer() {
        return aEdgePointer;
    }

    int getActiveLength() {
        return activeLength;
    }

    char getActiveEdge() {
        return suffixTree.edgeMap[aEdgePointer];
    }


}
