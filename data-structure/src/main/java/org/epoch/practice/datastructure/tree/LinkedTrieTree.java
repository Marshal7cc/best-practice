package org.epoch.practice.datastructure.tree;

/**
 * 字典树 - 链表实现
 *
 * @author Marshal
 * @since 2022/10/9
 */
public class LinkedTrieTree {
    private final Node root = new Node();

    public static void main(String[] args) {
        LinkedTrieTree trieTree = new LinkedTrieTree();
        trieTree.insert("m");
        trieTree.insert("mars");
        trieTree.insert("marshal");
        trieTree.insert("a");
        trieTree.insert("ab");

        System.out.println(trieTree.search("m"));
        System.out.println(trieTree.search("mar"));
        System.out.println(trieTree.search("mars"));
        System.out.println(trieTree.search("marshal"));
        System.out.println(trieTree.search("ab"));
        System.out.println(trieTree.search("abc"));
    }

    public void insert(String s) {
        Node p = this.root;
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            int idx = arr[i] - 'a';
            if (p.next[idx] == null) {
                p.next[idx] = new Node();
            }
            p = p.next[idx];
        }
        p.cnt++;
    }

    public boolean search(String s) {
        Node p = this.root;
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            int idx = arr[i] - 'a';
            if (p.next[idx] == null) {
                return false;
            }
            p = p.next[idx];
        }
        return p.cnt != 0;
    }

    static class Node {
        private int cnt;
        private Node[] next = new Node[26];
    }
}
