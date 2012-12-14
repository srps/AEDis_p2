package SuffixTrees;

import java.util.BitSet;

/**
 * User: Sergio Silva   - 55457
 * User: Marco Ferreira - 56886
 * Date: 10-12-2012
 * Time: 15:25
 */

public class SuffixTree {
    protected int root;
    protected Node[] nodes;
    protected char[] edgeMap;
    int counter = 0;
    public int position = -1,                                           // position of the last added character
            currentNode = -1,                                           // index of the last added node
            needSuffixLink = -1,                                        // the node to which a suffix link will be added
            remainder = 0;

    ActivePoint aPoint;                                                 // active point representation

    public SuffixTree(int length) {
        nodes = new Node[2 * length + 2];
        edgeMap = new char[length];
        aPoint = new ActivePoint(this);
        setNodePointer(root = createNode(-1));
    }

    private void addSuffixLink(int node) {
        if (needSuffixLink > 0) {
            nodes[needSuffixLink].link = node;
        }
        needSuffixLink = node;
    }

    boolean compareNextNode(int next) {
        if (getActiveLength() >= nodes[next].edgeLength()) {                // if length of the edge <= active_length
            setEdgePointer(getEdgePointer() + nodes[next].edgeLength());
            setActiveLength(getActiveLength() - nodes[next].edgeLength());
            setNodePointer(next);
            return true;
        }
        return false;
    }

    int createNode(int start, int end) {
        nodes[++currentNode] = new Node(start, end, this);
        return currentNode;
    }

    int createNode(int start) {
        nodes[++currentNode] = new Node(start, this);
        return currentNode;
    }

    public void addChar(char c) throws Exception {
        c -= (c >= 'a') ? 'a' : 'A';
        edgeMap[++position] = c;
        // at the beginning of each step
        needSuffixLink = -1;                                                    // reset needSuffixLink
        remainder++;                                                            // and increase remainder
        while (remainder > 0) {
            if (getActiveLength() == 0) setEdgePointer(position);
            if (nodes[getNodePointer()].next[getActiveEdge()] == 0) {           // there is no edge starting with active_edge
                int leaf = createNode(position);                                       // adding a leaf node to active_node
                nodes[getNodePointer()].next[getActiveEdge()] = leaf;
                addSuffixLink(getNodePointer());
            } else {                                                            // there is an edge starting with active_edge
                int next = nodes[getNodePointer()].next[getActiveEdge()];
                if (compareNextNode(next)) {
                    continue;                                                   // compare next node if active_length >= (edge length)
                }
                if (edgeMap[nodes[next].start + getActiveLength()] == c) {      // character c is somewhere on the edge
                    addSuffixLink(getNodePointer());
                    setActiveLength(getActiveLength() + 1);
                    break;                                                       // do nothing, finish the step
                }                                                                // creating a split-node in the middle of the edge
                int split = createNode(nodes[next].start, nodes[next].start + getActiveLength()); // split node
                nodes[getNodePointer()].next[getActiveEdge()] = split;
                int leaf = createNode(position);                                 // node with single character on edge
                nodes[split].next[c] = leaf;
                nodes[next].start += getActiveLength();
                nodes[split].next[edgeMap[nodes[next].start]] = next;
                addSuffixLink(split);
            }

            remainder--;                                            // an insert took place - decrement remainder

            if (getNodePointer() == root && getActiveLength() > 0) { // after insertion, if we're at the root -
                setActiveLength(getActiveLength() - 1);               // decrement active_Length
                setEdgePointer(position - remainder + 1);           // and set active_edge to next_suffix_to_add[0]
            } else {                                                // otherwise - go to the suffix link (or root)
                setNodePointer(nodes[getNodePointer()].link > 0 ? nodes[getNodePointer()].link : root);
            }

        }
    }

    public int printOrderedString(int start, int length, BitSet capitals) {

        char converter;                             // check if the input was originally capitalized
        for (int node : nodes[start].next)                                  // for each possible node connected to this one
            if (node > 0) {                                                 // if it exists
                for (int i = nodes[node].start;                             // then check the edge connecting the two
                     i < nodes[node].end;
                     i++, counter++) {
                    if (counter > length) {                                 // we reached the end of our circular string
                        return 0;                                           // so we end the function
                    }
                    converter = capitals.get(counter) ? 'A' : 'a';
                    System.out.print((char) (edgeMap[i] + converter));      // print each individual character
                }
                printOrderedString(node, length, capitals);                // depth-first search, alphabetically
            }
        return 0;
    }

    char getActiveEdge() {
        return aPoint.getActiveEdge();
    }

    int getNodePointer() {
        return aPoint.getNodePointer();
    }

    int getEdgePointer() {
        return aPoint.getEdgePointer();
    }

    int getActiveLength() {
        return aPoint.getActiveLength();
    }

    void setNodePointer(int value) {
        aPoint.setNodePointer(value);
    }

    void setEdgePointer(int value) {
        aPoint.setEdgePointer(value);
    }

    void setActiveLength(int value) {
        aPoint.setActiveLength(value);
    }

    public int getRoot() {
        return root;
    }
}







