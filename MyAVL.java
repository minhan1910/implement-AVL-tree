package avltree;

import java.util.LinkedList;
import java.util.Queue;

public class MyAVL {
    BinaryNode root;

    MyAVL() {
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

    private int getHeight(BinaryNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    private int getBalance(BinaryNode node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    private int updateHeightOf(BinaryNode node) {
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    private BinaryNode rotateLeft(BinaryNode disbalancedNode) {
        BinaryNode newRoot = disbalancedNode.right;
        disbalancedNode.right = disbalancedNode.right.left;
        newRoot.left = disbalancedNode;
        disbalancedNode.height = updateHeightOf(disbalancedNode);
        newRoot.height = updateHeightOf(newRoot);
        return newRoot;
    }

    private BinaryNode rotateRight(BinaryNode disbalancedNode) {
        BinaryNode newRoot = disbalancedNode.left;
        disbalancedNode.left = disbalancedNode.left.right;
        newRoot.right = disbalancedNode;
        disbalancedNode.height = updateHeightOf(disbalancedNode);
        newRoot.height = updateHeightOf(newRoot);
        return newRoot;
    }

    private BinaryNode insert(BinaryNode node, int nodeValue) {
        if (node == null) {
            BinaryNode newNode = new BinaryNode();
            newNode.value = nodeValue;
            newNode.height = 1;
            return newNode;
        }

        if (nodeValue < node.value)
            node.left = insert(node.left, nodeValue);
        else
            node.right = insert(node.right, nodeValue);

        node.height = updateHeightOf(node);
        int balance = getBalance(node);

        /*
         * Balace factors:
         * 0 is balance
         * 1 is left-heavy
         * 2 is right-heavy
         * 
         * 1 - left left condition
         * 2 - left right condition
         * 3 - right right condition
         * 4 - right left condition
         */

        // left left condition
        if (balance > 1 && nodeValue < node.left.value)
            return rotateRight(node);

        // left right condition
        if (balance > 1 && nodeValue > node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        // right right condition
        if (balance < -1 && nodeValue > node.right.value)
            return rotateLeft(node);

        // right left condition
        if (balance < -1 && nodeValue < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public void insert(int nodeValue) {
        root = insert(root, nodeValue);
    }

    /**
     * Chỗ này quan trọng vì phải check trước cái .left
     * chứ chỉ check node != null thì cái case
     * nếu .left = null rùi cho nó chạy thì nó sẽ get cái node về null
     * mà đáng ra phải trả về cái node hiện tại nếu .left khác != null
     */
    private BinaryNode getMinimumNode(BinaryNode node) {
        if (node.left == null)
            return node;

        BinaryNode temp = node;
        while (temp.left != null)
            temp = temp.left;

        return temp;
    }

    private BinaryNode deleteNodeNotRequiredRotation(BinaryNode node) {
        if (node == null)
            return node;
        if (node.left != null && node.right != null) {
            BinaryNode temp = node;
            BinaryNode minNodeFromRight = getMinimumNode(temp.right);
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

    private BinaryNode rebalancingAfterDeleteNode(BinaryNode node, int balanceFactor) {
        // Left left case
        if (balanceFactor > 1 && getBalance(node.left) >= 0)
            return rotateRight(node);
        // left right case
        if (balanceFactor > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // right right case
        if (balanceFactor < -1 && getBalance(node.right) < 0)
            return rotateLeft(node);

        if (balanceFactor < -1 && getBalance(node.right) >= 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        // node have balanced - 0
        return node;
    }

    private BinaryNode deleteNode(BinaryNode node, int nodeValue) {
        if (node == null)
            return null;
        if (nodeValue < node.value)
            node.left = deleteNode(node.left, nodeValue);
        else if (nodeValue > node.value)
            node.right = deleteNode(node.right, nodeValue);
        else
            node = deleteNodeNotRequiredRotation(node);

        return rebalancingAfterDeleteNode(node, getBalance(node));
    }

    public void delete(int value) {
        root = deleteNode(root, value);
    }
}
