package SuffixTrees;

/**
 * Created with IntelliJ IDEA.
 * User: Sergio
 * Date: 10-12-2012
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class SuffixTree {

    int counter = 0;
    final int ALPHABET_SIZE = 26;
    public int position = -1,                                              // position of the last added character
            currentNode = -1,                                           // index of the last added node
            needSuffixLink = -1,                                        // the node to which a suffix link will be added
            remainder = 0;

    ActivePoint aPoint = new ActivePoint();                             // active point

    public SuffixTree(int length) {
        aPoint.nodes = new Node[2 * length + 2];
        aPoint.edges = new char[length];
        aPoint.pseudoRoot = newNode(-1);                                // pseudo root (for convenience)
        aPoint.root = aPoint.activeNode = newNode(-1);
        for (int i = 0; i < ALPHABET_SIZE; i++) {                        // there is a path from "pseudo root" to root
            aPoint.nodes[aPoint.pseudoRoot].next[i] = 1;                // through each character from alphabet
        }
        aPoint.nodes[aPoint.root].link = 0;                             // link root to pseudo root
    }

    private void addSuffixLink(int node) {
        if (needSuffixLink > 0) {
            aPoint.nodes[needSuffixLink].link = node;
        }
        needSuffixLink = node;
    }

    char active_edge() {
        return aPoint.edges[aPoint.activeEdge];
    }

    boolean walkDown(int next) {
        if (aPoint.activeLength >= aPoint.nodes[next].edgeLength()) {   // if (length of the edge) <= active_length
            aPoint.activeEdge += aPoint.nodes[next].edgeLength();       // shift active_edge by the length of the edge
            aPoint.activeLength -= aPoint.nodes[next].edgeLength();     // decrease active length
            aPoint.activeNode = next;                                   // and move active_node to "next"
            return true;
        }
        return false;
    }

    int newNode(int start, int end) {
        aPoint.nodes[++currentNode] = new Node(start, end, this);
        return currentNode;
    }

    int newNode(int start) {
        aPoint.nodes[++currentNode] = new Node(start, this);
        return currentNode;
    }

    public void addChar(char c) throws Exception {
        c -= (c >= 'a') ? 'a' : 'A';
        aPoint.edges[++position] = c;
        // at the beginning of each step
        needSuffixLink = -1;                                                        // reset needSuffixLink
        remainder++;                                                                // and increase remainder
        while (remainder > 0) {
            if (aPoint.activeLength == 0) aPoint.activeEdge = position;
            if (aPoint.nodes[aPoint.activeNode].next[active_edge()] == 0) {         // there is no edge starting with active_edge
                int leaf = newNode(position);                                       // adding a leaf node to active_node
                aPoint.nodes[aPoint.activeNode].next[active_edge()] = leaf;
                addSuffixLink(aPoint.activeNode);
            } else {                                                                // there is an edge starting with active_edge
                int next = aPoint.nodes[aPoint.activeNode].next[active_edge()];
                if (walkDown(next))
                    continue;                                       // walk down if active_length >= (edge length)
                if (aPoint.edges[aPoint.nodes[next].start + aPoint.activeLength] == c) { // character c is somewhere on the edge
                    addSuffixLink(aPoint.activeNode);
                    aPoint.activeLength++;
                    break;                                                          // do nothing, finish the step
                }                                                                   // creating a split-node in the middle of the edge
                int split = newNode(aPoint.nodes[next].start, aPoint.nodes[next].start + aPoint.activeLength); // split node
                aPoint.nodes[aPoint.activeNode].next[active_edge()] = split;
                int leaf = newNode(position);                                       // node with single character on edge
                aPoint.nodes[split].next[c] = leaf;
                aPoint.nodes[next].start += aPoint.activeLength;
                aPoint.nodes[split].next[aPoint.edges[aPoint.nodes[next].start]] = next;
                addSuffixLink(split);
            }

            remainder--;                                                            // an insert took place - decrement remainder

            if (aPoint.activeNode == aPoint.root && aPoint.activeLength > 0) {             // after insertion, if we're at the root -
                aPoint.activeLength--;                                              // decrement active_Length
                aPoint.activeEdge = position - remainder + 1;                       // and set active_edge to next_suffix_to_add[0]
            } else {                                                                 // otherwise - go to the suffix link (or root)
                aPoint.activeNode = aPoint.nodes[aPoint.activeNode].link > 0 ? aPoint.nodes[aPoint.activeNode].link : aPoint.root;
            }

        }
    }

    public int printOrderedString(int start, int length, boolean isCapital) {

        char converter = isCapital ? 'A' : 'a';                             // check if the input was originally capitalized
        for (int node : aPoint.nodes[start].next)                           // for each possible node connected to this one
            if (node > 0) {                                                 // if it exists
                for (int i = aPoint.nodes[node].start;                      // then check the edge connecting the two
                     i < aPoint.nodes[node].edgeLength() + aPoint.nodes[node].start;
                     i++, counter++) {
                    if (counter > length) {                                 // we reached the end of our circular string
                        return 0;                                           // so we end the function
                    }
                    System.out.print((char) (aPoint.edges[i] + converter)); // print each individual character
                }
                printOrderedString(node, length, isCapital);           // depth-first search, alphabetically
            }
        return 0;
    }

    public int getRoot() {
        return aPoint.root;
    }
}







