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
    public void uniquePathsIIITest() {
        System.out.println(uniquePathsIII(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 2, -1}}));
        res = 0;
        System.out.println(uniquePathsIII(new int[][]{{0, 1}, {2, 0}}));
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
//    private List<String> trace = new ArrayList<>();
    private int res = 0;
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
        return res;
    }

    private void backtrace(int[][] grid, int travelCount, int totalMove, int row, int col) {
        // 退出条件
        if (grid[row][col] == 2) {
            if (travelCount == totalMove - 1) {
                res++;
            }
            return;
        }
        if (grid[row][col] != -1) {
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
//        输入：matrix = [[2,1,3],[6,5,4],[7,8,9]]
//        输出：13
//        minFallingPathSum(new int[][]{{2,1,3}, {6,5,4}, {7,8,9}});
//        输入：matrix = [[-19,57],[-40,-5]]
//        输出：-59
//        System.out.println(minFallingPathSum(new int[][]{{-19, 57}, {-40, -5}}));
        System.out.println(minFallingPathSum(new int[][]{{-48}}));
//        输出：-39
    }

}
