package SuffixTrees;

/**
 * Created with IntelliJ IDEA.
 * User: Sergio
 * Date: 10-12-2012
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class SuffixTree {

    final int ALPHABET_SIZE = 26;
    Node[] nodes;
    char[] text;
    public int root,                                                       // position of the root node
            position = -1,                                              // position of the last added character
            currentNode = -1,                                           // index of the last added node
            needSuffixLink = -1,                                        // the node to which a suffix link will be added
            remainder = 0;

    int active_node = 0,
            active_length = 0,
            active_edge = 0;                                            // active point

    public SuffixTree(int length) {
        nodes = new Node[2 * length + 2];
        text = new char[length];
        int pseudo = newNode(-1);                               // pseudo root (for convenience)
        root = active_node = newNode(-1);
        for (int i = 0; i < ALPHABET_SIZE; i++)                     // there is a path from "pseudo root" to root
            nodes[pseudo].next[i] = 1;                             // through each character from alphabet
        nodes[root].link = 0;                                       // link root to pseudo root
    }

    private void addSuffixLink(int node) {
        if (needSuffixLink > 0)
            nodes[needSuffixLink].link = node;
        needSuffixLink = node;
    }

    char active_edge() {
        return text[active_edge];
    }

    boolean walkDown(int next) {
        if (active_length >= nodes[next].edgeLength()) {            // if (length of the edge) <= active_length
            active_edge += nodes[next].edgeLength();                // shift active_edge by the length of the edge
            active_length -= nodes[next].edgeLength();              // decrease active length
            active_node = next;                                     // and move active_node to "next"
            return true;
        }
        return false;
    }

    int newNode(int start, int end) {
        nodes[++currentNode] = new Node(start, end, this);
        return currentNode;
    }

    int newNode(int start) {
        nodes[++currentNode] = new Node(start, this);
        return currentNode;
    }

    public void addChar(char c) throws Exception {
        c -= 'a';
        text[++position] = c;
        // at the beginning of each step
        needSuffixLink = -1;                                        // reset needSuffixLink
        remainder++;                                                // and increase remainder
        while (remainder > 0) {
            if (active_length == 0) active_edge = position;
            if (nodes[active_node].next[active_edge()] == 0) {       // there is no edge starting with active_edge
                int leaf = newNode(position);                   // adding a leaf node to active_node
                nodes[active_node].next[active_edge()] = leaf;
                addSuffixLink(active_node);
            } else {                                                // there is an edge starting with active_edge
                int next = nodes[active_node].next[active_edge()];
                if (walkDown(next)) continue;                       // walk down if active_length >= (edge length)
                if (text[nodes[next].start + active_length] == c) { // character c is somewhere on the edge
                    addSuffixLink(active_node);
                    active_length++;
                    break;                                          // do nothing, finish the step
                }                                                   // creating a split-node in the middle of the edge
                int split = newNode(nodes[next].start, nodes[next].start + active_length); // split node
                nodes[active_node].next[active_edge()] = split;
                int leaf = newNode(position);                   // node with single character on edge
                nodes[split].next[c] = leaf;
                nodes[next].start += active_length;
                nodes[split].next[text[nodes[next].start]] = next;
                addSuffixLink(split);
            }

            remainder--;                                            // an insert took place - decrement remainder

            if (active_node == root && active_length > 0) {         // after insertion, if we're at the root -
                active_length--;                                    // decrement active_Length
                active_edge = position - remainder + 1;             // and set active_edge to next_suffix_to_add[0]
            } else                                                  // otherwise - go to the suffix link (or root)
                active_node = nodes[active_node].link > 0 ? nodes[active_node].link : root;


        }
    }

    public int countDistinctSubstrings(int x) {                     // the function that counts the number of
        int res = 0;                                                // distinct substrings.
        if (x != root)                                              // the result is the sum of all edges' length
            res += nodes[x].edgeLength();
        for (int node : nodes[x].next)
            if (node > 0)
                res += countDistinctSubstrings(node);
        return res;
    }
}







