package org.epoch.practice.datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Marshal
 * @description handwriting red-black tree
 */
public class RBT<E extends Comparable<E>> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public Node root;
    private int size;

    public static void main(String[] args) {
        RBT rbt = new RBT();
        rbt.addEle(35);
        rbt.addEle(42);
        rbt.addEle(22);
        rbt.levelOrder();
    }

    public int size() {
        return size;
    }

    /**
     * 添加节点,递归
     *
     * @param e
     */
    public void addEle(E e) {
        root = addEle(root, e);
        root.color = BLACK;
    }

    //   node                     x
    //  /   \     左旋转         /  \
    // T1   x   --------->   node   T3
    //     / \              /   \
    //    T2 T3            T1   T2

    /**
     * 将元素E添加到以node为根的那个树上并且将新树的根返回
     *
     * @param node 当前递归子树的根节点
     * @param e    待插入节点
     * @return 新的跟节点
     */
    private Node addEle(Node node, E e) {
        // node == null
        // 1 root为空
        // 2 已经递归到叶子节点(红黑树叶子节点是null)
        // 那么这个添加节点就是当前递归下的根节点
        if (node == null) {
            size++;
            return new Node(e);
        }

        // 小于node的值，往左递归，否则往右递归
        if (e.compareTo(node.e) < 0) {
            node.left = addEle(node.left, e);
        } else {
            node.right = addEle(node.right, e);
        }

        // 底层递归Node已经添加完毕，外层递归开始维护平衡
        // ==============================
        //左旋
        // Condition:1右侧是红 2 左侧非红
        if (isRed(node.right) && !isRed(node.left)) {
            node = leftRotate(node);
        }

        //右旋
        // Condition:1 左红 2 左子红
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }

        //颜色翻转
        // Condition:1右侧是红 2 左侧红
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    private Node leftRotate(Node node) {
        Node x = node.right;

        node.right = x.left;
        x.left = node;

        x.color = node.color;
        node.color = RED;
        return x;

    }

    //     node                   x
    //    /   \     右旋转       /  \
    //   x    T2   ------->   y   node
    //  / \                       /  \
    // y  T1                     T1  T2

    /**
     * 颜色翻转
     *
     * @param node
     */
    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    private Node rightRotate(Node node) {
        Node x = node.left;

        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;

        return x;

    }

    /**
     * 判断节点是否为红节点，叶子节点一定是黑节点
     *
     * @param node
     * @return
     */
    private boolean isRed(Node node) {
        if (node == null) return BLACK;
        return node.color;
    }

    /**
     * 层次遍历树
     */
    public void levelOrder() {
        if (root == null) {
            return;
        }
        Queue<Node> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node curNode = queue.remove();
            System.out.println(curNode);
            if (curNode.left != null) {
                queue.add(curNode.left);
            }
            if (curNode.right != null) {
                queue.add(curNode.right);
            }
        }
    }

    /**
     * 红黑树节点
     */
    private class Node {
        public E e;
        public Node left;
        public Node right;
        public boolean color;

        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
            this.color = RED;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "e=" + e +
                    ", color=" + color +
                    '}';
        }
    }
}
