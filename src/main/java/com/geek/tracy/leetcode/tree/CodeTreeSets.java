package com.geek.tracy.leetcode.tree;

import com.geek.tracy.leetcode.tree.bean.Node;
import com.geek.tracy.leetcode.tree.bean.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
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
     * 590.N叉树的后续遍历
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
}
