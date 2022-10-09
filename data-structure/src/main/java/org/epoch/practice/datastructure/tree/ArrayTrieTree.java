package org.epoch.practice.datastructure.tree;

/**
 * 字典树 - 数组实现
 *
 * @author Marshal
 * @since 2022/10/9
 */
public class ArrayTrieTree {
    private static final int N = 10000;
    private int[][] trie = new int[N][26];
    // cnt[i]代表编号为i的字母作为最后一个字母的次数
    private int[] cnt = new int[N];
    // 每个字母的编号
    private int seq = 0;

    public static void main(String[] args) {
        ArrayTrieTree trieTree = new ArrayTrieTree();
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
        int p = 0;
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            int idx = arr[i] - 'a';
            if (trie[p][idx] == 0) {
                trie[p][idx] = seq++;
            }
            p = trie[p][idx];
        }
        cnt[p]++;
    }

    public boolean search(String s) {
        int p = 0;
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            int idx = arr[i] - 'a';
            if (trie[p][idx] == 0) {
                return false;
            }
            p = trie[p][idx];
        }
        return cnt[p] != 0;
    }
}
