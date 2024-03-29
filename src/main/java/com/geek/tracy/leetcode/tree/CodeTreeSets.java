package com.geek.tracy.leetcode.tree;

import com.geek.tracy.leetcode.tree.bean.Node;
import com.geek.tracy.leetcode.tree.bean.TreeNode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;

/**
 * 树 -- 相关题目
 * @Author Tracy
 * @Date 2023/6/26
 */
public class CodeTreeSets extends TreeTraversal {

    /**
     * 2415.反转二叉树的奇数层
     */
    public TreeNode reverseOddLevels(TreeNode root) {
        return null;
    }


    @Test
    public void goodNodesTest() {

    }

    /**
     * 1448. 统计二叉树中好节点的数目
     *
     * 给你一棵根为 root 的二叉树，请你返回二叉树中好节点的数目。
     * 「好节点」X 定义为：从根到该节点 X 所经过的节点中，没有任何节点的值大于 X 的值。
     */
    public int goodNodes(TreeNode root) {
        return getGoodNode(root, Integer.MIN_VALUE);
    }

    private int getGoodNode(TreeNode note, int curMax) {
        if (note == null) {
            return 0;
        }
        if (note.val >= curMax) {
            return 1 + getGoodNode(note.left, note.val) + getGoodNode(note.right, note.val);
        } else {
            return getGoodNode(note.left, curMax) + getGoodNode(note.right, curMax);
        }
    }


    /**
     * 617. 合并二叉树
     *
     * 给你两棵二叉树： root1 和 root2 。
     *
     * 想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的一些节点将会重叠（而另一些不会）。你需要将这两棵树合并成一棵新二叉树。合并的规则是：
     * 如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；否则，不为 null 的节点将直接作为新二叉树的节点。
     * 返回合并后的二叉树。
     * 注意: 合并过程必须从两个树的根节点开始。
     *
     * 答案写法，更加简洁，书写角度不一样
     *     public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
     *         if (t1 == null) {
     *             return t2;
     *         }
     *         if (t2 == null) {
     *             return t1;
     *         }
     *         TreeNode merged = new TreeNode(t1.val + t2.val);
     *         merged.left = mergeTrees(t1.left, t2.left);
     *         merged.right = mergeTrees(t1.right, t2.right);
     *         return merged;
     *     }
     *  来个更简洁的：上述解答的精简版
     *    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
     *         if (root1 == null) return root2;
     *         if (root2 == null) return root1;
     *         return new TreeNode(root1.val + root2.val,
     *             mergeTrees(root1.left, root2.left),    // 合并左子树
     *             mergeTrees(root1.right, root2.right)); // 合并右子树
     *     }
     *
     * 作者：灵茶山艾府
     * 链接：https://leetcode.cn/problems/merge-two-binary-trees/solutions/2387255/kan-dao-di-gui-jiu-yun-dai-ni-li-jie-di-leixm/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     *
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 != null && root2 != null) {
            root1.val = root1.val + root2.val;
            root1.left = mergeTrees(root1.left, root2.left);
            root1.right = mergeTrees(root1.right, root2.right);
        } else if (root1 == null && root2 == null) {
            return null;
        } else if (root1 == null) {
            root1 = new TreeNode(root2.val);
            root1.left = mergeTrees(null, root2.left);
            root1.right = mergeTrees(null, root2.right);
        }
        return root1;

    }


    /**
     * 题单：贡献法
     * 891. 子序列宽度之和，题解
     * 907. 子数组的最小值之和，题解
     * 1856. 子数组最小乘积的最大值
     * 2104. 子数组范围和
     * 2681. 英雄的力量
     * 2281. 巫师的总力量和
     *
     * 979. 在二叉树中分配硬币 distribute coins in binary tree
     *
     * 给定一个有 N 个结点的二叉树的根结点 root，树中的每个结点上都对应有 node.val 枚硬币，并且总共有 N 枚硬币。
     * 在一次移动中，我们可以选择两个相邻的结点，然后将一枚硬币从其中一个结点移动到另一个结点。(移动可以是从父结点到子结点，或者从子结点移动到父结点。)。
     * 返回使每个结点上只有一枚硬币所需的移动次数。
     *
     * 注：有点不好理解！！待进一步理解！
     */
    private int res = 0;
    public int distributeCoins(TreeNode root) {
        dfsCoin(root);
        return res;
    }

    /**
     * 深度优先算法
     */
    private int dfsCoin(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = dfsCoin(root.left);
        int right = dfsCoin(root.right);
        res += Math.abs(left) + Math.abs(right);
        return left + right + root.val - 1;
    }

    /**
     * 144. 二叉树的前序遍历
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        // 前序遍历
        preOrder(root, list);
        return list;
    }

    /**
     * 94. 二叉树的中序遍历
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        // 中序遍历
        inOrder(root, list);
        return list;
    }

    /**
     * 145. 二叉树的后序遍历
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        // 后续序遍历
        postOrder(root, list);
        return list;
    }

    /**
     * 590.N叉树的后序遍历
     */
    public List<Integer> postorder(Node root) {
        // 递归访问N叉树：以此访问字数1，2，3...，最后根节点
        List<Integer> ans = new ArrayList<>();
        dfs(root, ans);
        return ans;
    }

    public void dfs(Node root, List<Integer> ans) {
        if (root == null) return;
        // 依次访问子树
        for (Node child : root.children) {
            dfs(child, ans);
        }
        ans.add(root.val);
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        if (n == 0) return null;
        if (n == 1) return new TreeNode(preorder[0]);
        // 左子树长度
        int leftSubSize = 0;
        for (int i : inorder) {
            if (i != preorder[0]) {
                leftSubSize++;
            } else {
                break;
            }
        }
        // 按前序、中序特点，截取左子树前序、中序，右子树前序、中序
        int[] leftPre  = Arrays.copyOfRange(preorder, 1, leftSubSize + 1);
        int[] leftIn = Arrays.copyOfRange(inorder, 0, leftSubSize);
        int[] rightPre = Arrays.copyOfRange(preorder, leftSubSize + 1, n);
        int[] rightIn = Arrays.copyOfRange(inorder, leftSubSize + 1, n);
        return new TreeNode(preorder[0], buildTree(leftPre, leftIn), buildTree(rightPre, rightIn));
    }


    /**
     * 106.从中序与后序遍历序列构造二叉树 -- 分治法递归处理子问题
     */
    public TreeNode buildTreeInPost(int[] inorder, int[] postorder) {
        int n = inorder.length;
        if (n == 0) return null;
        if (n == 1) return new TreeNode(inorder[0]);

        // 左子树长度：遍历中序数组，找到postorder[n - 1]时，左侧长度即为左子树长度
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (postorder[n - 1] == inorder[i]) {
                break;
            } else {
                count++;
            }
        }
        // 截取左子树中序、后序，右子树中序、后续
        int[] leftTreeInorder = Arrays.copyOfRange(inorder, 0, count);
        int[] leftTreePostorder = Arrays.copyOfRange(postorder, 0, count);
        int[] rightTreeInorder = Arrays.copyOfRange(inorder,count + 1, n);
        int[] rightTreePostorder = Arrays.copyOfRange(postorder, count, n - 1);
        // 构建树：TreeNode(根节点值， 左子树， 右子树)
        return new TreeNode(postorder[n - 1], buildTree(leftTreeInorder, leftTreePostorder), buildTree(rightTreeInorder, rightTreePostorder));
    }

    @Test
    public void test_106() {
        // 输入：inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
        //输出：[3,9,20,null,null,15,7]
    }

    /**
     * 889.根据前序和后序遍历构造二叉树
     *
     * 分析：仅通过前序、后续，得到的解不唯一，可以拆分为子问题处理，即分治法，递归处理子问题。通过前序的根，找到对应的左右子树递归处理，前序数组为1时返回节点，为空时返回null
     */
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        // 前序数组为1时返回节点，为空时返回null
        int length = preorder.length;
        if (length == 0) return null;
        if (length == 1) return new TreeNode(preorder[0]);

        // 找左子树的长度：遍历后序遍历数组，直到rootValue的值，其长度即为rootValue为根的左子树长度
        int count = 0;
        for (int i = 0; i < postorder.length; i++) {
            count++;
            if (postorder[i] == preorder[1]) {
               break;
           }
        }
        int leftTreeSize = count;
        // 画出示意实例进行分析设值
        int[] leftPreOrder = Arrays.copyOfRange(preorder, 1, leftTreeSize + 1);    // 左子树-前序遍历
        int[] leftPostOrder = Arrays.copyOfRange(postorder, 0, leftTreeSize);   // 左子树-后序遍历
        int[] rightPreOrder = Arrays.copyOfRange(preorder, leftTreeSize + 1, length);   // 右子树-前序遍历
        int[] rightPostOrder = Arrays.copyOfRange(postorder, leftTreeSize, length - 1);  // 右子树-后序遍历

        return new TreeNode(preorder[0], constructFromPrePost(leftPreOrder, leftPostOrder), constructFromPrePost(rightPreOrder, rightPostOrder));
    }

    @Test
    public void test_889() {
        // 输入：preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
        //输出：[1,2,3,4,5,6,7]
        TreeNode treeNode = constructFromPrePost(new int[]{1, 2, 4, 5, 3, 6, 7}, new int[]{4, 5, 2, 6, 7, 3, 1});
    }


    /**
     * 2583.二叉树中的第K大层和
     *
     * 给你一棵二叉树的根节点 root 和一个正整数 k 。
     * 树中的 层和 是指 同一层 上节点值的总和。
     * 返回树中第 k 大的层和（不一定不同）。如果树少于 k 层，则返回 -1 。
     * 注意，如果两个节点与根节点的距离相同，则认为它们在同一层。
     */
    public long kthLargestLevelSum(TreeNode root, int k) {
        // 计算每一层的和，层序遍历
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        List<Long> sum = new ArrayList<>();
        // 栈保存某一层所有节点
        while (!stack.isEmpty()) {
            List<TreeNode> list = new ArrayList<>(); // 保存当前层的节点
            Long levelSum = 0L;
            for (TreeNode treeNode : stack) {
                list.add(treeNode);
                levelSum += treeNode.val;
            }
            sum.add(levelSum);
            stack.clear();
            for (TreeNode treeNode : list) {
                if (treeNode.left != null) stack.push(treeNode.left);
                if (treeNode.right != null) stack.push(treeNode.right);
            }
        }
        sum.sort(Comparator.reverseOrder());// 倒叙
        return sum.size() < k ? -1 : sum.get(k - 1);

    }

    @Test
    public void test_2583() {
        // 输入：root = [5,8,9,2,1,3,7,4,6], k = 2
        //输出：13
        //解释：树中每一层的层和分别是：
        //- Level 1: 5
        //- Level 2: 8 + 9 = 17
        //- Level 3: 2 + 1 + 3 + 7 = 13
        //- Level 4: 4 + 6 = 10
        //第 2 大的层和等于 13 。
        TreeNode root = constructFromPrePost(new int[]{5,8,2,4,6,1,9,3,7}, new int[]{4,6,2,1,8,3,7,9,5});
        long l = kthLargestLevelSum(root, 2);
        Assert.assertEquals(13, l);
        // 输入：root = [1,2,null,3], k = 1
        //输出：3
        //解释：最大的层和是 3 。

    }

}
