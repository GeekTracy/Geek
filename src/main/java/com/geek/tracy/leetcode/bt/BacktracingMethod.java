package com.geek.tracy.leetcode.bt;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 回溯算法 Backtracking Algorithm
 * @Author Tracy
 * @Date 2023/7/13
 */
public class BacktracingMethod {


    @Test
    public void minFallingPathSumIITest() {
        // 超时
        System.out.println(minFallingPathSumII(new int[][]{
                {-2, -18, 31, -10, -71, 82, 47, 56, -14, 42},
                {-95, 3, 65, -7, 64, 75, -51, 97, -66, -28},
                {36, 3, -62, 38, 15, 51, -58, -90, -23, -63},
                {58, -26, -42, -66, 21, 99, -94, -95, -90, 89},
                {83, -66, -42, -45, 43, 85, 51, -86, 65, -39},
                {56, 9, 9, 95, -56, -77, -2, 20, 78, 17},
                {78, -13, -55, 55, -7, 43, -98, -89, 38, 90},
                {32, 44, -47, 81, -1, -55, -5, 16, -81, 17},
                {-87, 82, 2, 86, -88, -58, -91, -79, 44, -9},
                {-96, -14, -52, -8, 12, 38, 84, 77, -51, 52}}));

    }

    /**
     * 1289. 下降路径最小和 II
     *
     * 给你一个 n x n 整数矩阵 grid ，请你返回 非零偏移下降路径 数字和的最小值。
     * 非零偏移下降路径 定义为：从 grid 数组中的每一行选择一个数字，且按顺序选出来的数字中，相邻数字不在原数组的同一列。
     *
     * 此回溯算法会超时，可使用动态规划法
     */
    private int MIN_II = 1000;
    public int minFallingPathSumII(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            List<Integer> trace = new ArrayList<>();
            backtrace02(grid, 0, i, trace);
        }
        return MIN_II;
    }

    private void backtrace02(int[][] grid, int row, int col, List<Integer> trace) {
        int length = grid.length;
        // 退出条件
        if (trace.size() == grid[0].length - 1) {
            trace.add(grid[row][col]);
            int sum = 0;
            for (Integer item : trace) {
                sum += item;
            }
            MIN_II = Math.min(MIN_II, sum);
            return;
        }
        // 访问
        trace.add(grid[row][col]);
        // 遍历下一行的非col列
        for (int i = row + 1; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (col == j) continue;
                backtrace02(grid, i, j, trace);
                // 回退
                trace.remove(trace.size() - 1);
            }
        }
    }



    @Test
    public void uniquePathsIIITest() {
        System.out.println(uniquePathsIII(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 2, -1}}));
//        res = 0;
//        System.out.println(uniquePathsIII(new int[][]{{0, 1}, {2, 0}}));
    }

    /**
     * 980. 不同路径 III
     *
     *
     *  [1, 0, 0, 0],
     *  [0, 0, 0, 0],
     *  [0, 0, 2,-1]
     *
     * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
     * 2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
     *
     * 回溯遍历二维数组所有节点
     *
     */
    private List<String> trace = new ArrayList<>(); // 临时保存访问路径
    private List<String> result = new ArrayList<>(); // 最终有效路径
    private int i = 0;
    private int j = 0;
    public int uniquePathsIII(int[][] grid) {
        i = grid.length;
        j = grid[0].length;
        int totalCount = 0;

        int startrow = 0;
        int startcol = 0;
        for (int k = 0; k < i; k++) {
            for (int l = 0; l < j; l++) {
                if (grid[k][l] != -1) totalCount++;
                if (grid[k][l] == 1) {
                    startrow = k;
                    startcol = l;
                }
            }
        }
        backtrace(grid, 0, totalCount, startrow, startcol);
        return result.size();
    }

    private void backtrace(int[][] grid, int travelCount, int totalMove, int row, int col) {
        // 退出条件
        if (grid[row][col] == 2) {
            if (travelCount == totalMove - 1) {
                trace.add("(" + row + ", " + col + ")");
                result.add(trace.toString());
            }
            return;
        }
        if (grid[row][col] != -1) {
            trace.add("(" + row + ", " + col + ")");
            int curr = grid[row][col];
            grid[row][col] = -1;
            if (col > 0 && grid[row][col - 1] != -1) {
                // 向左
                backtrace(grid, travelCount + 1, totalMove, row, col - 1);
            }
            if (col < j - 1 && grid[row][col + 1] != -1) {
                // 向右
                backtrace(grid, travelCount + 1, totalMove, row, col + 1);
            }
            if (row > 0 && grid[row - 1][col] != -1) {
                // 向上
                backtrace(grid, travelCount + 1, totalMove, row - 1, col);
            }
            if (row < i - 1 && grid[row + 1][col] != -1) {
                // 向下
                backtrace(grid, travelCount + 1, totalMove, row + 1, col);
            }
            grid[row][col] = curr;
            trace.remove(trace.size() - 1);
        }
    }


    private static int MIN = Integer.MAX_VALUE;

    /**
     * 931. 下降路径最小和
     *
     * 提示：
     *      n == matrix.length == matrix[i].length
     *      1 <= n <= 100
     *      -100 <= matrix[i][j] <= 100
     */
    public int minFallingPathSum(int[][] matrix) {
        List<Integer> traversal = new ArrayList<>();
        for (int col = 0; col < matrix.length; col++) {
            backtracking01(matrix, traversal, 0, col);
        }
        return MIN;
    }

    public void backtracking01(int[][] matrix, List<Integer> traversal, int row, int col) {
        // 推出条件
        if (row == matrix.length) {
            MIN = Math.min(MIN, traversal.stream().mapToInt(Integer::valueOf).sum());
            return;
        }
        // 遍历列
        for (int j = 0; j < matrix.length; j++) {
            if (j >= col - 1 && j <= col + 1) {
                traversal.add(matrix[row][j]);
                // 访问下一行
                backtracking01(matrix, traversal, row + 1, j);
                // 回退
                traversal.remove(traversal.size() - 1);
            }
        }
    }

    @Test
    public void minFallingPathSumTest() {
//        输入：matrix = [[2,1,3},{6,5,4],[7,8,9]]
//        输出：13
//        minFallingPathSum(new int[][]{{2,1,3}, {6,5,4}, {7,8,9}});
//        输入：matrix = [[-19,57],[-40,-5]]
//        输出：-59
//        System.out.println(minFallingPathSum(new int[][]{{-19, 57}, {-40, -5}}));
        System.out.println(minFallingPathSum(new int[][]{{-48}}));
//        输出：-39
    }

}
