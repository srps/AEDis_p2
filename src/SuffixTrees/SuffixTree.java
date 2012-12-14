package SuffixTrees;

import java.util.BitSet;

/**
 * User: Sergio Silva   - 55457
 * User: Marco Ferreira - 56886
 * Date: 10-12-2012
 * Time: 15:25
 */

public class SuffixTree {
    private final int root;
    private final Node[] nodes;
    final char[] edgeMap;
    private int counter = 0;
    public int position = -1;                                           // position of  last added character
    private int currentNode = -1;                                       // index of  last added node
    private int needSuffixLink = -1;                                    //  node where suffix will be added
    private int remainder = 0;

    private final ActivePoint aPoint;                                   // active point representation

    /**
     * @param length - length of the input
     */
    public SuffixTree(int length) {
        nodes = new Node[2 * length + 2];                               // 2 nodes per input char + root
        edgeMap = new char[length];                                     // only needs one char per input char
        aPoint = new ActivePoint(this);                                 // initialize activePoint with the root
        setNodePointer(root = createNode(-1));                          // sets the node pointer to the freshly created root
    }

    /**
     * @param node - adds suffix link from needed to this node
     */
    private void addSuffixLink(int node) {
        if (needSuffixLink > 0) {
            nodes[needSuffixLink].link = node;
        }
        needSuffixLink = node;
    }

    /*
        Verifica se é necessário ir ao proximo nó
        Se precisar avança o edgePointer, diminui o activeLength
        e coloca o nó a apontar para ele
     */

    /**
     * @param next - pointer to the node down the tree
     * @return - true if it was needed to walk down the next node; false otherwise
     */
    boolean walkDown(int next) {
        if (getActiveLength() >= nodes[next].edgeLength()) {
            setEdgePointer(getEdgePointer() + nodes[next].edgeLength());
            setActiveLength(getActiveLength() - nodes[next].edgeLength());
            setNodePointer(next);
            return true;
        }
        return false;
    }

    /**
     * @param start - specifies the node start char in the charMap
     * @param end   - specifies the node end char in the charMap
     * @return - returns the int pointer to the node
     */
    int createNode(int start, int end) {
        nodes[++currentNode] = new Node(start, end, this);
        return currentNode;
    }

    /**
     * @param start - specifies the node start char in the charMap
     * @return - returns the int pointer to the node
     */
    int createNode(int start) {
        nodes[++currentNode] = new Node(start, this);
        return currentNode;
    }

    /**
     * @param c - char to be inserted
     */
    public void insertCharTree(char c) {
        c -= (c >= 'a') ? 'a' : 'A';
        edgeMap[++position] = c;
        // at the beginning of each step
        needSuffixLink = -1;                                                    // reset needSuffixLink
        remainder++;                                                            // increase remainder (there's a char to be inserted)
        while (remainder > 0) {
            if (getActiveLength() == 0)
                setEdgePointer(position);               // resets edge pointer to last char added if activeLength is zero
            if (nodes[getNodePointer()].next[getActiveEdge()] == 0) {           // there is no edge starting with active_edge
                int leaf = createNode(position);                                // add a leaf node to active_node
                nodes[getNodePointer()].next[getActiveEdge()] = leaf;
                addSuffixLink(getNodePointer());
            } else {                                                            // there is an edge starting with active_edge
                int next = nodes[getNodePointer()].next[getActiveEdge()];
                if (walkDown(next)) {
                    continue;                                                   // walkDown if active_length >= (edge length)
                }
                if (edgeMap[nodes[next].start + getActiveLength()] == c) {      // character c is on the edge
                    addSuffixLink(getNodePointer());
                    setActiveLength(getActiveLength() + 1);
                    break;
                }
                int split = createNode(nodes[next].start, nodes[next].start + getActiveLength()); // split node
                nodes[getNodePointer()].next[getActiveEdge()] = split;
                int leaf = createNode(position);                                 // node with single character on edge
                nodes[split].next[c] = leaf;
                nodes[next].start += getActiveLength();
                nodes[split].next[edgeMap[nodes[next].start]] = next;
                addSuffixLink(split);                                            // adds a suffix link to split node
            }

            remainder--;                                            //  decrement remainder

            /*
                After insertion, if we're at root -> decrement activeLength and set edge pointer to next suffix,
                otherwise go to suffix link (or root)
            */
            if (getNodePointer() == root && getActiveLength() > 0) {
                setActiveLength(getActiveLength() - 1);
                setEdgePointer(position - remainder + 1);
            } else {
                setNodePointer(nodes[getNodePointer()].link > 0 ? nodes[getNodePointer()].link : root);
            }

        }
    }

    /**
     * @param start    - the starting looking node pointer (0 = root)
     * @param length   - length of the input sentence
     * @param capitals - bitset to correctly print capital letters
     */
    public void printOrderedString(int start, int length, BitSet capitals) {

        char converter;
        for (int node : nodes[start].next)                                  // for each possible node connected to this one
            if (node > 0) {                                                 // if it exists
                for (int i = nodes[node].start;                             // then check the edge connecting the two
                     i < nodes[node].end;
                     i++, counter++) {
                    if (counter > length) {                                 // we reached the end of our circular string
                        return;                                             // so we end the function
                    }
                    converter = capitals.get(counter) ? 'A' : 'a';          // check if the input was originally capitalized
                    System.out.print((char) (edgeMap[i] + converter));      // print each individual character
                }
                printOrderedString(node, length, capitals);                 // depth-first search, alphabetically
            }
    }

    /**
     * @return - return the activeEdge
     */
    char getActiveEdge() {
        return aPoint.getActiveEdge();
    }

    /**
     * @return - returns the activeNode pointer
     */
    int getNodePointer() {
        return aPoint.getNodePointer();
    }

    /**
     * @return - returns the activeEdge pointer
     */
    int getEdgePointer() {
        return aPoint.getEdgePointer();
    }

    /**
     * @return - returns the activeLength
     */
    int getActiveLength() {
        return aPoint.getActiveLength();
    }

    /**
     * @param value - value to set the activeNode pointer
     */
    void setNodePointer(int value) {
        aPoint.setNodePointer(value);
    }

    /**
     * @param value - value to set the activeEdge pointer
     */
    void setEdgePointer(int value) {
        aPoint.setEdgePointer(value);
    }

    /**
     * value to set the active length
     *
     * @param value - value to set the activeLength
     */
    void setActiveLength(int value) {
        aPoint.setActiveLength(value);
    }

    /**
     * @return - returns the root node pointer (0)
     */
    public int getRoot() {
        return root;
    }
}







