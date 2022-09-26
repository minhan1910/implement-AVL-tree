package avltree;

import java.util.LinkedList;
import java.util.Queue;

public class AVL {
    BinaryNode root;

    AVL() {
        root = null;
    }

    public void preOrder(BinaryNode node) {
        if (node == null)
            return;

        System.out.print(node.value + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder(BinaryNode node) {
        if (node == null)
            return;

        inOrder(node.left);
        System.out.print(node.value + " ");
        inOrder(node.right);
    }

    public void postOrder(BinaryNode node) {
        if (node == null)
            return;

        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.value + " ");
    }

    public void levelOrder(BinaryNode node) {
        if (node == null)
            return;

        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(node);

        while (!queue.isEmpty()) {
            BinaryNode presentNode = queue.remove();
            System.out.print(presentNode.value + " ");

            if (presentNode.left != null)
                queue.add(presentNode.left);
            if (presentNode.right != null)
                queue.add(presentNode.right);
        }
    }

    public BinaryNode searchNode(BinaryNode node, int value) {
        if (node == null)
            return null;

        if (value < node.value)
            return searchNode(node.left, value);
        else if (value > node.value)
            return searchNode(node.right, value);
        else
            return node;
    }

    // get height
    public int getHeight(BinaryNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // rotateRight
    private BinaryNode rotateRight(BinaryNode disbalancedNode) {
        BinaryNode newRoot = disbalancedNode.left;
        disbalancedNode.left = disbalancedNode.left.right;
        newRoot.right = disbalancedNode;
        disbalancedNode.height = 1 + Math.max(getHeight(disbalancedNode.left), getHeight(disbalancedNode.right));
        newRoot.height = 1 + Math.max(getHeight(newRoot.left), getHeight(newRoot.right));
        return newRoot;
    }

    // rotateLeft
    private BinaryNode rotateLeft(BinaryNode disbalancedNode) {
        BinaryNode newRoot = disbalancedNode.right;
        disbalancedNode.right = disbalancedNode.right.left;
        newRoot.left = disbalancedNode;
        disbalancedNode.height = 1 + Math.max(getHeight(disbalancedNode.left), getHeight(disbalancedNode.right));
        newRoot.height = 1 + Math.max(getHeight(newRoot.left), getHeight(newRoot.right));
        return newRoot;
    }

    // getBalance
    public int getBalance(BinaryNode node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    // insertNode method
    private BinaryNode insertNode(BinaryNode node, int nodeValue) {
        if (node == null) {
            BinaryNode newNode = new BinaryNode();
            newNode.value = nodeValue;
            newNode.height = 1;
            return newNode;
        } else if (nodeValue < node.value)
            node.left = insertNode(node.left, nodeValue);
        else
            node.right = insertNode(node.right, nodeValue);

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balance = getBalance(node);

        // left left condition
        if (balance > 1 && nodeValue < node.left.value) {
            return rotateRight(node);
        }

        // left right condition
        if (balance > 1 && nodeValue > node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // right right condition
        if (balance < -1 && nodeValue > node.right.value) {
            return rotateLeft(node);
        }

        // right left condition
        if (balance < -1 && nodeValue < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public void insert(int value) {
        this.root = insertNode(root, value);
    }

    // Minimum node
    private static BinaryNode minimumNode(BinaryNode root) {
        if (root.left == null)
            return root;
        return minimumNode(root.left);
    }

    private BinaryNode rebalanceAfterDeletingNode(BinaryNode node, int balance) {
        // left left condtion
        if (balance > 1 && getBalance(node.left) >= 0)
            return rotateRight(node);
        // left right condition
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // right right condition
        if (balance < -1 && getBalance(node.right) <= 0)
            return rotateLeft(node);
        // right left condition
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private BinaryNode deleteNodeNotRotateRequired(BinaryNode node) {
        // is leaf node
        // one children
        // two children

        if (node.left != null && node.right != null) {
            BinaryNode temp = node;
            BinaryNode minNodeFromRight = minimumNode(temp.right);
            node.value = minNodeFromRight.value;
            node.right = deleteNode(node.right, minNodeFromRight.value);
        } else if (node.left != null)
            node = node.left;
        else if (node.right != null)
            node = node.right;
        else
            node = null;

        return node;
    }

    // delete node
    private BinaryNode deleteNode(BinaryNode node, int value) {
        if (node == null)
            return node;
        if (value < node.value)
            node.left = deleteNode(node.left, value);
        else if (value > node.value)
            node.right = deleteNode(node.right, value);
        else
            node = deleteNodeNotRotateRequired(node);

        return rebalanceAfterDeletingNode(node, getBalance(node));
    }

    public void delete(int value) {
        root = deleteNode(root, value);
    }
}
