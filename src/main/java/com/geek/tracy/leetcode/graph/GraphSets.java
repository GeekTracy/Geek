package com.geek.tracy.leetcode.graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * 图的遍历
 *
 * @author mike
 * @date 2024/7/18
 */
public class GraphSets {


    /**
     * 3112.访问消失节点的最少时间
     * <p>
     * Dijkstra 算法介绍
     */
    public int[] minimumTime(int n, int[][] edges, int[] disappear) {
        return null;
    }


    @Test
    public void test_2101() {
        // 输入：bombs = [[2,1,3],[6,1,4]]
        //输出：2
//        Assert.assertEquals(2, maximumDetonation(new int[][]{{2, 1, 3}, {6, 1, 4}}));

        // 输入：bombs = [[1,1,5],[10,10,5]]
        //输出：1
//        Assert.assertEquals(1, maximumDetonation(new int[][]{{1,1,5}, {10,10,5}}));

        // 输入：bombs = [[1,2,3],[2,3,1],[3,4,2],[4,5,3],[5,6,4]]
        //输出：5
        Assert.assertEquals(5, maximumDetonation(new int[][]{{1,2,3}, {2,3,1},{3,4,2},{4,5,3},{5,6,4}}));


    }

    /**
     * 2101.引爆最多的炸弹
     * 题目：给你一个炸弹列表。一个炸弹的 爆炸范围 定义为以炸弹为圆心的一个圆。
     * 炸弹用一个下标从 0 开始的二维整数数组 bombs 表示，其中 bombs[i] = [xi, yi, ri] 。xi 和 yi 表示第 i 个炸弹的 X 和 Y 坐标，ri
     * 表示爆炸范围的 半径 。
     * 你需要选择引爆 一个 炸弹。当这个炸弹被引爆时，所有 在它爆炸范围内的炸弹都会被引爆，这些炸弹会进一步将它们爆炸范围内的其他炸弹引爆。
     * 给你数组 bombs ，请你返回在引爆 一个 炸弹的前提下，最多 能引爆的炸弹数目。
     * <p>
     * 提示：
     * 1 <= bombs.length <= 100
     * bombs[i].length == 3
     * 1 <= xi, yi, ri <= 10^5
     * <p>
     * 解题思路：构造图：每个节点可以引爆其他节点的图Map<Integer, List<Integer>>，然后进行广度遍历，增加vis[]数组标记已访问的节点，
     * 获取可引爆的其他节点
     */
    public int maximumDetonation(int[][] bombs) {
        int num = bombs.length;
        Map<Integer, List<Integer>> graph = new HashMap<>(); // key:炸弹下标，value:key下标可以引爆的炸弹下标集合

        for (int i = 0; i < num; i++) {
            for (int k = 0; k < num; k++) {
                // 判断i炸弹 是否能引爆 k炸弹
                if (i == k) {
                    continue; // 排他
                }
                // 如果i炸弹可以引爆k炸弹，即i与k圆心的距<=ri，（xi - xk）^2 + （yi - yk）^2 <= ri * ri
                if (canBomb(bombs, i, k)) {
                    graph.computeIfAbsent(i, key -> new ArrayList<>()).add(k);
                }
            }
        }
        int ans = 0; // 记录引爆的炸弹数
        // 遍历每个炸弹爆炸可以引爆的炸弹数 ans
        for (int i = 0; i < num; i++) {
            Deque<Integer> deque = new ArrayDeque<>();
            // 初始化deque
            deque.push(i);
            boolean[] vis = new boolean[num]; // 标记下标节点是否访问
            vis[i] = true;
            int curSum = 1;
            while (!deque.isEmpty()) {
                Integer popIndex = deque.pop();
                if (graph.containsKey(popIndex)) {
                    List<Integer> bombList = graph.get(popIndex);
                    for (Integer bomb : bombList) {
                        if (!vis[bomb]) {
                            deque.push(bomb);
                            curSum++;
                            vis[bomb] = true;
                        }
                    }
                }
            }
            ans = Math.max(ans, curSum);
        }
        return ans;
    }

    /**
     * 即i与k圆心的距<=ri，（xi - xk）^2 + （yi - yk）^2 <= ri * ri，即i可以引爆k
     *
     * @return
     */
    private boolean canBomb(int[][] bombs, int i, int k) {
        // 距离平方和
        // 由于距离平方和超过Integer上限，平方和使用long
        long distance = ((long) (bombs[i][0] - bombs[k][0]) * (bombs[i][0] - bombs[k][0])) + ((long) (bombs[i][1] - bombs[k][1]) * (bombs[i][1] - bombs[k][1]));
        if (distance <= (long) bombs[i][2] * bombs[i][2]) {
            return true;
        }
        return false;
    }

}
