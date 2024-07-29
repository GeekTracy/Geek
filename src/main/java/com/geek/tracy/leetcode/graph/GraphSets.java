package com.geek.tracy.leetcode.graph;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * 网格图（DFS 深度优先算法,BFS 广度优先算法）
 *
 * @author mike
 * @date 2024/7/18
 */
public class GraphSets {


    /**
     * #############################################################################################
     * ### 网格图的BFS遍历
     */

    @Test
    public void test_542() {
        // 输入：mat = [[0,0,0],[0,1,0],[0,0,0]]
        //输出：[[0,0,0],[0,1,0],[0,0,0]]
        updateMatrix(new int[][]{{0,0,0}, {0,1,0}, {0,0,0}});
        //输入：mat = [[0,0,0],[0,1,0],[1,1,1]]
        //输出：[[0,0,0],[0,1,0],[1,2,1]]
    }
    /**
     * 542. 01 矩阵
     * @param matrix
     * @return
     */
    public int[][] updateMatrix(int[][] matrix) {
        Queue<int[]> queue = new LinkedList<>();
        int[] directions = {-1, 0, 1, 0, -1};

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                if (matrix[row][col] == 0) {
                    queue.offer(new int[] {row, col});
                } else {
                    //标记非零元素为负，和遍历后设定的正数距离加以区分
                    matrix[row][col] = -1;
                }
            }
        }

        int step = 1;
        while (!queue.isEmpty()) {
            //对当前队列中所有零元素遍历，所有元素向四周走一步
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                //获取队列中的元素位置
                int[] cur = queue.poll();
                //向四个方向依次走一步
                for (int j = 0; j < directions.length - 1; j++) {
                    int x = cur[0] + directions[j];
                    int y = cur[1] + directions[j + 1];
                    //如果超出矩阵范围，或者遇见零元素及设置过距离step的元素则跳过，只对未遍历到的-1操作
                    if (x < 0 || x >= matrix.length || y < 0 || y >= matrix[0].length || matrix[x][y] >= 0) {
                        continue;
                    }
                    matrix[x][y] = step;
                    queue.offer(new int[] {x, y});
                }
            }
            //下次遍历到的-1元素相比前一次距离step加1
            step++;
        }
        return matrix;
    }

     /**
     *
     * ## 网格图的BFS遍历  END-----------------
     * --------------------------------------
     */



     /**
     * #############################################################################################
     * ### 网格图的DFS遍历，类似于树的遍历，例如树的遍历：1）判断 base case，2）访问两个相邻结点：左子结点、右子结点
     * --  void traverse(TreeNode root) {
     * --      // 1）判断 base case
     * --      if (root == null) {
     * --          return;
     * --      }
     * --      // 2）访问两个相邻结点：左子结点、右子结点
     * --      traverse(root.left);
     * --      traverse(root.right);
     * --  }
     *
     * ### 而图的遍历同样分为2步，1）判断 base case，2）访问可能的相邻结点
     * ### 对于二位网格图grid[][]固定模式如下：
     *
     * -- void dfs(int[][] grid, int r, int c) {
     * --     // 判断 base case
     * --     // 如果坐标 (r, c) 超出了网格范围，直接返回
     * --     if (!inArea(grid, r, c)) {
     * --         return;
     * --     }
     * --     // 访问上、下、左、右四个相邻结点
     * --     dfs(grid, r - 1, c);
     * --     dfs(grid, r + 1, c);
     * --     dfs(grid, r, c - 1);
     * --     dfs(grid, r, c + 1);
     * -- }
     * --
     * -- // 判断坐标 (r, c) 是否在网格中
     * -- boolean inArea(int[][] grid, int r, int c) {
     * --     return 0 <= r && r < grid.length
     * --         	&& 0 <= c && c < grid[0].length;
     * -- }
     *
     * #############################################################################################
     */

    /**
     * 695.岛屿的最大面积
     * <p>
     * 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设 grid 的四个边缘
     * 都被 0（代表水）包围着。
     * 岛屿的面积是岛上值为 1 的单元格的数目。
     * 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
     */
    public int maxAreaOfIsland(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int maxArea = 0;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                // 发现岛屿，深度优先遍历，计算岛屿面积
                if (grid[r][c] == 1) {
                    maxArea = Math.max(maxArea, dfs_area(grid, r, c));
                }
            }
        }
        return maxArea;
    }

    private int dfs_area(int[][] grid, int r, int c) {
        if (!inArea(grid, r, c)) {
            // 边界判断：求岛屿面积，不在范围即面积为0
            return 0;
        }
        // 【先污染后治理】的理念，上下左右遍历网格图，递归调用进来，出界了再治理返回0
        if (grid[r][c] != 1) {  // 不是岛屿的，返回0
            return 0;
        }
        // 防止重复访问，是岛屿的替换为2
        grid[r][c] = '2';

        return 1 + dfs_area(grid, r - 1, c)
                + dfs_area(grid, r + 1, c)
                + dfs_area(grid, r, c - 1)
                + dfs_area(grid, r, c + 1);
    }

    /**
     * 行r、列c不超过网格图的边界
     */
    private boolean inArea(int[][] grid, int r, int c) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }

    /**
     * 200.岛屿数量
     * <p>
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     * 此外，你可以假设该网格的四条边均被水包围。
     *
     * @param grid
     * @return 岛屿的个数
     */
    public int numIslands(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int numIslands = 0;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                // 遍历所有节点，发现岛屿，就dfs深度优先遍历，将该岛屿所有节点标记为2，岛屿总数加1
                if (grid[r][c] == '1') {
                    dfs_num(grid, r, c);
                    numIslands++;
                }
            }
        }
        return numIslands;
    }

    /**
     * 遍历网格图，将岛屿标记为2
     * @param grid
     * @param r
     * @param c
     */
    private void dfs_num(char[][] grid, int r, int c) {
        // 边界判断
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length) {
            return;
        }
        // 不是岛屿
        if (grid[r][c] != '1') {
            return;
        }
        grid[r][c] = '2';
        // 访问上下左右
        dfs_num(grid, r - 1, c);
        dfs_num(grid, r + 1, c);
        dfs_num(grid, r, c - 1);
        dfs_num(grid, r, c + 1);
    }

    /**
     * 463.岛屿的周长
     *
     * 基于上面2道岛屿问题，这里求周长，即遇到边界、遇到水时，返回长度1即可。本题限定了网格图中只有1个岛屿
     */
    public int islandPerimeter(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                // 遍历所有节点，发现岛屿，就dfs深度优先遍历，将该岛屿所有节点标记为2，岛屿总数加1
                if (grid[r][c] == 1) {
                    return dfs_perimeter(grid, r, c);
                }
            }
        }
        return 0;
    }

    private int dfs_perimeter(int[][] grid, int r, int c) {
        // 边界判断
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length) {
            // 因为求周长，遇到边界时，返回边长1
            return 1;
        }
        // 遇到水时，返回边长1
        if (grid[r][c] == 0) {
            return 1;
        }
        if (grid[r][c] != 1) {
            return 0;  // 不是岛屿，此题仅可为2，即访问过的，长度不变
        }
        // 岛屿标记为2，表示已访问过
        grid[r][c] = 2;
        // 上下左右，遍历访问
        return dfs_perimeter(grid, r - 1, c) + dfs_perimeter(grid, r + 1, c)
                + dfs_perimeter(grid, r, c - 1) + dfs_perimeter(grid, r, c + 1);

    }

    /**
     * 16.19.水域大小
     *
     * 你有一个用于表示一片土地的整数矩阵land，该矩阵中每个点的值代表对应地点的海拔高度。若值为0则表示水域。由垂直、水平或对角连接的水域为池塘。
     * 池塘的大小是指相连接的水域的个数。编写一个方法来计算矩阵中所有池塘的大小，返回值需要从小到大排序。
     */
    public int[] pondSizes(int[][] land) {
        List<Integer> ans = new ArrayList<>();
        int row = land.length;
        int col = land[0].length;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (land[r][c] == 0) {
                    ans.add(dfs_pond(land, r, c));
                }
            }
        }
        Collections.sort(ans);
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 水域的遍历为八个方向：上下左右对角线反向。遍历到水域0，将其标记为-1
     */
    private int dfs_pond(int[][] land, int r, int c) {
        // 1）边界
        if (r < 0 || c < 0 || r >= land.length || c >= land[0].length) {
            return 0 ;
        }
        if (land[r][c] != 0) {
            return 0;
        }
        land[r][c] = -1;  // 标记为-1，表示已遍历过
        // 2）遍历其他节点，上下左右对角线
        return 1 + dfs_pond(land, r - 1, c)
                + dfs_pond(land, r + 1, c)
                + dfs_pond(land, r, c - 1)
                + dfs_pond(land, r, c + 1)
                + dfs_pond(land, r - 1, c - 1)
                + dfs_pond(land, r - 1, c + 1)
                + dfs_pond(land, r + 1, c - 1)
                + dfs_pond(land, r + 1, c + 1);
    }

    /**
     *
     * ## 网格图的DFS遍历  END-----------------
     * --------------------------------------
     **/

    /**
     * 3112.访问消失节点的最少时间
     * <p>
     * Dijkstra 算法介绍
     */
    public int[] minimumTime(int n, int[][] edges, int[] disappear) {
        return null;
    }


    @Test
    public void test_547() {
        // 输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
        //输出：2
        Assert.assertEquals(2, findCircleNum(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}));
        // 输入：isConnected = [[1,0,0],[0,1,0],[0,0,1]]
        //输出：3
        Assert.assertEquals(3, findCircleNum(new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}));

    }

    /**
     * 547.省份数量
     */
    public int findCircleNum(int[][] isConnected) {
        List<int[]> connectList = new ArrayList<>();
        int length = isConnected.length;
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < isConnected[0].length; col++) {
                if (row != col && isConnected[row][col] == 1) {
                    connectList.add(new int[]{row, col});
                }
            }
        }
        // 根据下表，建立map
        Map<Integer, List<int[]>> map = new HashMap<>();
        for (int[] ints : connectList) {
            map.computeIfAbsent(ints[0], key -> new ArrayList<>()).add(ints);
            map.computeIfAbsent(ints[1], key -> new ArrayList<>()).add(ints);
        }
        int count = 0;
        boolean[] vis = new boolean[length];
        Deque<Integer> deque = new ArrayDeque<>();
        // 遍历访问每一个节点
        for (int i = 0; i < length; i++) {
            if (vis[i]) {
                continue;
            }
            deque.add(i);
            vis[i] = true;

            while (!deque.isEmpty()) {
                Integer pop = deque.pollFirst();
                List<int[]> ints = map.get(pop);
                if (ints == null) {
                    continue;
                }
                for (int[] anInt : ints) {
                    if (!vis[anInt[0]]) {
                        deque.offerLast(anInt[0]);
                        vis[anInt[0]] = true;
                    }
                    if (!vis[anInt[1]]) {
                        deque.offerLast(anInt[1]);
                        vis[anInt[1]] = true;
                    }
                }
            }
            count++;
        }
        return count;
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
        Assert.assertEquals(5, maximumDetonation(new int[][]{{1, 2, 3}, {2, 3, 1}, {3, 4, 2}, {4, 5, 3}, {5, 6, 4}}));


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
