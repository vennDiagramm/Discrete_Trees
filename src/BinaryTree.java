class BinaryTree {
    // Definition for a binary tree node
    static class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    // Root of the binary tree
    Node root;

    // Constructor
    BinaryTree() {
        root = null;
    }

    // Insert a new node with given key
    void insert(int key) {
        root = insertRec(root, key);
    }

    // Recursive function to insert a new node with given key
    Node insertRec(Node root, int key) {
        // If the tree is empty, return a new node
        if (root == null) {
            root = new Node(key);
            return root;
        }

        // Otherwise, recur down the tree
        if (key < root.key) {
            root.left = insertRec(root.left, key);
        } else if (key > root.key) {
            root.right = insertRec(root.right, key);
        }

        // Return the (unchanged) node pointer
        return root;
    }

    // This method mainly calls inOrderRec()
    void inOrder() {
        inOrderRec(root);
    }

    // A utility function to do in-order traversal of the binary tree
    void inOrderRec(Node root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.print(root.key + " ");
            inOrderRec(root.right);
        }
    }

    // This method mainly calls preOrderRec()
    void preOrder() {
        preOrderRec(root);
    }

    // A utility function to do pre-order traversal of the binary tree
    void preOrderRec(Node root) {
        if (root != null) {
            System.out.print(root.key + " ");
            preOrderRec(root.left);
            preOrderRec(root.right);
        }
    }

    // This method mainly calls postOrderRec()
    void postOrder() {
        postOrderRec(root);
    }

    // A utility function to do post-order traversal of the binary tree
    void postOrderRec(Node root) {
        if (root != null) {
            postOrderRec(root.left);
            postOrderRec(root.right);
            System.out.print(root.key + " ");
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        /* Let us create the following binary tree
                50
              /    \
             30     70
            /  \   /  \
           20  40 60  80 */
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);

        // Print in-order traversal of the binary tree
        System.out.println("In-order traversal:");
        tree.inOrder();
        System.out.println();

        // Print pre-order traversal of the binary tree
        System.out.println("Pre-order traversal:");
        tree.preOrder();
        System.out.println();

        // Print post-order traversal of the binary tree
        System.out.println("Post-order traversal:");
        tree.postOrder();
        System.out.println();
    }
}