import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;

class Node {
    char ch;
    int freq;
    Node left, right;

    Node(char ch, int freq, Node left, Node right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}

public class HuffmanTree {
    // Method to compare the nodes for the priority queue
    static class NodeComparator implements Comparator<Node> {
        public int compare(Node x, Node y) {
            return x.freq - y.freq;
        }
    }

    public static Node getHuffmanTree(String text) {
        if (text.length() == 0) {
            return null;
        }

        Map<Character, Integer> freq = new HashMap<>();
        for (char ch : text.toCharArray()) {
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            pq.add(new Node(entry.getKey(), entry.getValue(), null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();
            int newFreq = left.freq + right.freq;
            pq.add(new Node('\0', newFreq, left, right));
        }

        return pq.poll();
    }

    public static void main(String[] args) {
        String text = "ADBADEDBBDDD";
        Node root = getHuffmanTree(text);

        // The following code can be added to print or use the tree, if needed.
    }
}
