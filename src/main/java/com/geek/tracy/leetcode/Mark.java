package com.geek.tracy.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 难题，经典汇总mark
 *
 * @author Tracy
 * @date 2024/1/19
 */
public class Mark {

    @Test
    public void test_699() {
        //
        fallingSquares(new int[][]{{50,47},{95,48},{88,77},{84,3},{53,87},{98,79},{88,28},{13,22},{53,73},{85,55}});
    }

    /**
     * 699.掉落的方块
     * 分析+调试
     */
    public List<Integer> fallingSquares(int[][] positions) {
        int len = positions.length;
        int[] maxHere = new int[len];
        int[] realHere = new int[len];
        maxHere[0] = positions[0][1];
        realHere[0] = positions[0][1];
        for (int i = 1; i < len; i++) {
            // 从 i - 1从前找到j，第一个left+r > positions[i][0]的，高度在maxHere[j]加positions[i][1]，否则高度为positions[i][1]
            for (int j = i - 1; j >= 0; j--) {
                // 节点j抵达的右侧坐标
                int jRight = positions[j][0] + positions[j][1];
                int jLeft = positions[j][0];
                if (jRight > positions[i][0] && jLeft < positions[i][0] + positions[i][1]) {
                    // 节点j的右侧大于i节点的左侧坐标，则i可以放到j上，放上去的高度为：maxHere[j] + positions[i][1]
                    // maxHere[i]取放上去的高度与maxHere[i - 1]中较大的
                    realHere[i] = Math.max(realHere[j] + positions[i][1], realHere[i]);
                    maxHere[i] = Math.max(maxHere[i], Math.max(maxHere[i - 1], realHere[j] + positions[i][1]));
                }
            }
            // 遍历完i之前的所有节点，如果没有找到符合条件的j，则maxHere[i]高度等于节点i的r与maxHere[i - 1]中较大的
            if (maxHere[i] == 0) {
                // 没有找到可以放上去的j点
                realHere[i] = positions[i][1];
                maxHere[i] = Math.max(maxHere[i - 1], positions[i][1]);
            }
        }
        return Arrays.stream(maxHere).boxed().collect(Collectors.toList());
    }

    @Test
    public void test_2866 () {
        Assert.assertEquals(22, maximumSumOfHeightsII(Arrays.asList(6, 5, 3, 9, 2, 7)));
    }

    /**
     * 2866.美丽塔 Ⅱ
     *  范围增大，暴力解法超时，需要使用单调栈。
     *  美丽塔即是一个有峰顶的山，做半段0--i单点递增，右半段i+1--n-1单调递减；遍历maxHeights，计算当i为峰顶时，满足条件的美丽塔的和。右半段
     *  从n-1 -- i，定义个数组suf[j]表示j下标节点后的美丽塔和的最大值。
     *
     * 思路：单调栈
     */
    public long maximumSumOfHeightsII(List<Integer> maxHeights) {
        int[] a = maxHeights.stream().mapToInt(i -> i).toArray();
        int n = a.length;
        long[] suf = new long[n + 1];
        Deque<Integer> st = new ArrayDeque<Integer>();
        st.push(n); // 哨兵：存放临界点处的值，用于临界点兼容
        long sum = 0;
        for (int i = n - 1; i >= 0; i--) {
            int x = a[i];
            while (st.size() > 1 && x <= a[st.peek()]) {
                int j = st.pop();
                sum -= (long) a[j] * (st.peek() - j); // 撤销之前加到 sum 中的
            }
            sum += (long) x * (st.peek() - i); // 从 i 到 st.peek()-1 都是 x
            suf[i] = sum;
            st.push(i);
        }

        long ans = sum;
        st.clear();
        st.push(-1); // 哨兵：存放临界点处的值，用于临界点兼容
        long pre = 0;
        for (int i = 0; i < n; i++) {
            int x = a[i];
            while (st.size() > 1 && x <= a[st.peek()]) {
                int j = st.pop();
                pre -= (long) a[j] * (j - st.peek()); // 撤销之前加到 pre 中的
            }
            pre += (long) x * (i - st.peek()); // 从 st.peek()+1 到 i 都是 x
            ans = Math.max(ans, pre + suf[i + 1]);
            st.push(i);
        }
        return ans;

    }

    @Test
    public void test_42() {
        Assert.assertEquals(6, trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        Assert.assertEquals(9, trap(new int[]{4, 2, 0, 3, 2, 5}));
    }

    /**
     * 42.接雨水
     * <p>
     * 提示：
     * <p>
     * n == height.length
     * 1 <= n <= 2 * 10^4
     * 0 <= height[i] <= 10^5
     * 思路：单调栈
     */
    public int trap(int[] height) {
        int len = height.length;
        // 遍历2次，分别获取从左往右，从右往左的当前最大值数组maxToRight,maxToLeft
        int[] arrMaxToRight = new int[len];
        int[] arrMaxToLeft = new int[len];
        int toRightMax = 0;
        int toLetfMax = 0;
        for (int i = 0; i < height.length; i++) {
            arrMaxToRight[i] = Math.max(toRightMax, height[i]);
            toRightMax = arrMaxToRight[i];
        }
        for (int i = height.length - 1; i >= 0; i--) {
            arrMaxToLeft[i] = Math.max(toLetfMax, height[i]);
            toLetfMax = arrMaxToLeft[i];
        }
        int ans = 0;
        for (int i = 0; i < height.length; i++) {
            ans += Math.min(arrMaxToRight[i] - height[i], arrMaxToLeft[i] - height[i]);
        }
        return ans;
    }

    /**
     * 2809.使数组和小于等于x的最少时间
     * <p>
     * 给你两个长度相等下标从 0 开始的整数数组 nums1 和 nums2 。每一秒，对于所有下标 0 <= i < nums1.length ，nums1[i] 的值都增加 nums2[i] 。
     * 操作 完成后 ，你可以进行如下操作：
     * <p>
     * 选择任一满足 0 <= i < nums1.length 的下标 i ，并使 nums1[i] = 0 。
     * 同时给你一个整数 x 。
     * <p>
     * 请你返回使 nums1 中所有元素之和 小于等于 x 所需要的 最少 时间，如果无法实现，那么返回 -1
     */
    public int minimumTime(List<Integer> nums1, List<Integer> nums2, int x) {
        // 如果多次操作一个数，发现只有最后一次操作有效，之前的操作只会让其他数的值增大，因此每个数最多仅增加一次，即操作次数在[0, n]之间。
        int n = nums1.size();
        int[][] f = new int[n + 1][n + 1];
        int[][] nums = new int[n][2]; // nums数组，固定nums1,nums2数组相同下标的值，后面按nums2值排序时，保证对应位置的数不变
        int s1 = 0, s2 = 0;  // nums1/nums2 数组的和
        for (int i = 0; i < n; ++i) {
            s1 += nums1.get(i);
            s2 += nums2.get(i);
            nums[i] = new int[]{nums1.get(i), nums2.get(i)};
        }
        // 按照nums2数组的值，对nums1,nums2进行升序排序，即可按顺序进行操作
        Arrays.sort(nums, Comparator.comparingInt(a -> a[1]));
        for (int i = 1; i <= n; ++i) {
            for (int j = 0; j <= n; ++j) {
                f[i][j] = f[i - 1][j];
                if (j > 0) {
                    int a = nums[i - 1][0], b = nums[i - 1][1];
                    // 转移方程：f[i][j] = max{f[i−1][j], f[i−1][j−1] + nums1[i] + nums2[i]×j}
                    f[i][j] = Math.max(f[i][j], f[i - 1][j - 1] + a + b * j);
                }
            }
        }
        for (int j = 0; j <= n; ++j) {
            if (s1 + s2 * j - f[n][j] <= x) {
                return j;
            }
        }
        return -1;
    }

    //    作者：ylb
//    链接：https://leetcode.cn/problems/minimum-time-to-make-array-sum-at-most-x/solutions/2610313/python3javacgotypescript-yi-ti-yi-jie-pa-nyni/
    public int minimumTime02(List<Integer> nums1, List<Integer> nums2, int x) {
        int n = nums1.size();
        int[] f = new int[n + 1];
        int[][] nums = new int[n][0];
        int s1 = 0, s2 = 0;  // nums1/nums2 数组的和
        for (int i = 0; i < n; ++i) {
            s1 += nums1.get(i);
            s2 += nums2.get(i);
            nums[i] = new int[]{nums1.get(i), nums2.get(i)};
        }
        Arrays.sort(nums, Comparator.comparingInt(a -> a[1]));
        for (int[] e : nums) {
            int a = e[0], b = e[1];
            for (int j = n; j > 0; --j) {
                f[j] = Math.max(f[j], f[j - 1] + a + b * j);
            }
        }

        for (int j = 0; j <= n; ++j) {
            if (s1 + s2 * j - f[j] <= x) {
                return j;
            }
        }
        return -1;
    }


    @Test
    public void test_2809() {

    }


    /**
     * 31.下一个排列
     * <p>
     * 分析：题意即寻找下一个较大的排列，当当前排列是最大的排列时，返回最小的那个排列。
     * 例如：[1,2,3]，所有的排列有：[1,2,3]、[1,3,2]、[2,1,3]、[2,3,1]、[3,1,2]、[3,2,1]，按大小顺序排列，
     * 当当前序列是最后一个，即[3,2,1]时，下一个排列即为第一个[1,2,3]。
     * 1）从右往左找，找到第一个[i < j 且 nums[i] < nums[j] ]（升序）,则从j到end一定是递减；
     * 2）在[j,end]找到k，满足[nums[i] < nums[k] ]，交换i和k的值；
     * 3）i之后的区间一定为降序排列，将i之后调整为升序；
     * 4）如果第1步没有找到满足条件的i，j，说明nums为降序排列，即最大的那个排列，直接执行第3步将整个数组调整为升序；
     */
    public void nextPermutation(int[] nums) {
        int i = -1;
        int length = nums.length;
        for (int n = length - 1; n > 0; n--) {
            if (nums[n - 1] < nums[n]) {
                i = n - 1;
                break;
            }
        }
        // 特殊情况，原数组为降序
        if (i == -1) {
            reverse(nums, 0, length - 1);
            return;
        }
        int k = 0;
        for (int n = length - 1; n > i; n--) {
            if (nums[n] > nums[i]) {
                k = n;
                break;
            }
        }
        // 交换i 和 k，并将i之后的改为升序
        int temp = nums[k];
        nums[k] = nums[i];
        nums[i] = temp;

        // i + 1 --- end 倒叙排列
        reverse(nums, i + 1, length - 1);
        System.out.println("----end----");
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int i1 = nums[end];
            nums[end] = nums[start];
            nums[start] = i1;
            start++;
            end--;
        }
    }

    @Test
    public void test_31() {
        nextPermutation(new int[]{1, 2, 3, 8, 5, 7, 6, 4});
    }

    /**
     * 2846.边权重均等查询
     * <p>
     * 现有一棵由 n 个节点组成的无向树，节点按从 0 到 n - 1 编号。给你一个整数 n 和一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ui, vi, wi] 表示树中存在一条位于节点 ui 和节点 vi 之间、权重为 wi 的边。
     * <p>
     * 另给你一个长度为 m 的二维整数数组 queries ，其中 queries[i] = [ai, bi] 。对于每条查询，请你找出使从 ai 到 bi 路径上每条边的权重相等所需的 最小操作次数 。在一次操作中，你可以选择树上的任意一条边，并将其权重更改为任意值。
     * <p>
     * 注意：
     * <p>
     * 查询之间 相互独立 的，这意味着每条新的查询时，树都会回到 初始状态 。
     * 从 ai 到 bi的路径是一个由 不同 节点组成的序列，从节点 ai 开始，到节点 bi 结束，且序列中相邻的两个节点在树中共享一条边。
     * 返回一个长度为 m 的数组 answer ，其中 answer[i] 是第 i 条查询的答案。
     */
    public int[] minOperationsQueries(int n, int[][] edges, int[][] queries) {
        int length = queries.length;
        int[] ans = new int[length];
        Map<Integer, List<Integer>> indexMap = new HashMap<>(); // 保存edges起点值与其下标index
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            indexMap.computeIfAbsent(edge[0], key -> new ArrayList<>()).add(i);
            indexMap.computeIfAbsent(edge[1], key -> new ArrayList<>()).add(i);
        }
        for (int i = 0; i < length; i++) {
            int[] query = queries[i]; // 当前查询行
            int queryStart = query[0]; // 查询起始值
            int queryEnd = query[1]; // 查询结束值
            Deque<int[]> deque = new ArrayDeque<>(); // Stack
            Deque<int[]> temp = new ArrayDeque<>(); // 暂存Stack
            Map<Integer, Integer> weightCountMap = new HashMap<>();
            // 获取起始节点下标，如果只有一个，则继续往下找，直至找到queryEnd节点或无下一个节点；如果有多个，则将其入栈，一一遍历往下找
            while (true) {
                List<Integer> indexes = indexMap.get(queryStart);
                if (indexes.size() == 1) {
                    // 寻找到末端，未找到，弹出压栈节点
                    while (!deque.isEmpty() && !temp.isEmpty() && deque.peek() != temp.peek()) {
                        deque.pop();
                    }
                    // 找到deque中与temp栈顶相同的边edge，同时出栈，修改queryStart
                    if (!deque.isEmpty()) {
                        deque.pop();
                    }
                    if (!temp.isEmpty()) {
                        temp.pop();
                        if (!temp.isEmpty()) {
                            deque.push(temp.pop());
                            queryStart = queryStart == deque.peek()[0] ? deque.peek()[1] : deque.peek()[0];
                        }
                    }
                } else if (indexes.size() == 2) {
                    Integer currIndex = indexes.get(0);
                    int[] currEdge = edges[currIndex];
                    queryStart = currEdge[1]; // 修改查找的开始节点
                    deque.push(currEdge); // push stack
                    if (currEdge[1] == queryEnd || currEdge[0] == queryEnd) {
                        // 找到查询的末端节点，汇总栈中的所有节点，计算操作次数
                        int pNum = deque.size();
                        while (!deque.isEmpty()) {
                            int[] pop = deque.pop();
                            weightCountMap.merge(pop[2], 1, Integer::sum);
                        }
                        Integer max = weightCountMap.values().stream().max((Integer::compareTo)).get();
                        ans[i] = pNum - max;
                        break;
                    }
                } else {
                    indexes.forEach(index -> temp.push(edges[index])); // 多个出边入栈
                    deque.push(temp.peek()); // temp栈顶如deque栈
                    queryStart = queryStart == deque.peek()[0] ? deque.peek()[1] : deque.peek()[0]; // 更新查询起始值
                }
            }
        }
        return ans;
    }

    @Test
    public void test_2846() {
        //输入：n = 7, edges = [[0,1,1],[1,2,1],[2,3,1],[3,4,2],[4,5,2],[5,6,2]], queries = [[0,3],[3,6],[2,6],[0,6]]
        //输出：[0,0,1,3]
        Assert.assertArrayEquals(new int[]{0, 0, 1, 3}, minOperationsQueries(7,
                new int[][]{{0, 1, 1}, {1, 2, 1}, {2, 3, 1}, {3, 4, 2}, {4, 5, 2}, {5, 6, 2}}, new int[][]{{0, 3}, {3, 6}, {2, 6}, {0, 6}}));
        // 输入：n = 8, edges = [[1,2,6],[1,3,4],[2,4,6],[2,5,3],[3,6,6],[3,0,8],[7,0,2]], queries = [[4,6],[0,4],[6,5],[7,4]]
        //输出：[1,2,2,3]
        Assert.assertArrayEquals(new int[]{1, 2, 2, 3}, minOperationsQueries(8,
                new int[][]{{1, 2, 6}, {1, 3, 4}, {2, 4, 6}, {2, 5, 3}, {3, 6, 6}, {3, 0, 8}, {7, 0, 2}}, new int[][]{{4, 6}, {0, 4}, {6, 5}, {7, 4}}));
    }


    public int[] minOperationsQueriesII(int n, int[][] edges, int[][] queries) {
        List<int[]>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1], w = e[2] - 1;
            g[x].add(new int[]{y, w});
            g[y].add(new int[]{x, w});
        }

        int m = 32 - Integer.numberOfLeadingZeros(n); // n 的二进制长度
        int[][] pa = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(pa[i], -1);
        }
        int[][][] cnt = new int[n][m][26];
        int[] depth = new int[n];
        dfs(0, -1, g, pa, cnt, depth);

        for (int i = 0; i < m - 1; i++) {
            for (int x = 0; x < n; x++) {
                int p = pa[x][i];
                if (p != -1) {
                    int pp = pa[p][i];
                    pa[x][i + 1] = pp;
                    for (int j = 0; j < 26; j++) {
                        cnt[x][i + 1][j] = cnt[x][i][j] + cnt[p][i][j];
                    }
                }
            }
        }

        int[] ans = new int[queries.length];
        for (int qi = 0; qi < queries.length; qi++) {
            int x = queries[qi][0], y = queries[qi][1];
            int pathLen = depth[x] + depth[y];
            int[] cw = new int[26];
            if (depth[x] > depth[y]) {
                int temp = x;
                x = y;
                y = temp;
            }

            // 让 y 和 x 在同一深度
            for (int k = depth[y] - depth[x]; k > 0; k &= k - 1) {
                int i = Integer.numberOfTrailingZeros(k);
                int p = pa[y][i];
                for (int j = 0; j < 26; ++j) {
                    cw[j] += cnt[y][i][j];
                }
                y = p;
            }

            if (y != x) {
                for (int i = m - 1; i >= 0; i--) {
                    int px = pa[x][i];
                    int py = pa[y][i];
                    if (px != py) {
                        for (int j = 0; j < 26; j++) {
                            cw[j] += cnt[x][i][j] + cnt[y][i][j];
                        }
                        x = px;
                        y = py; // x 和 y 同时上跳 2^i 步
                    }
                }
                for (int j = 0; j < 26; j++) {
                    cw[j] += cnt[x][0][j] + cnt[y][0][j];
                }
                x = pa[x][0];
            }

            int lca = x;
            pathLen -= depth[lca] * 2;
            int maxCw = 0;
            for (int i = 0; i < 26; i++) {
                maxCw = Math.max(maxCw, cw[i]);
            }
            ans[qi] = pathLen - maxCw;
        }
        return ans;
    }

    /**
     * 深度优先
     */
    private void dfs(int x, int fa, List<int[]>[] g, int[][] pa, int[][][] cnt, int[] depth) {
        pa[x][0] = fa;
        for (int[] e : g[x]) {
            int y = e[0], w = e[1];
            if (y != fa) {
                cnt[y][0][w] = 1;
                depth[y] = depth[x] + 1;
                dfs(y, x, g, pa, cnt, depth);
            }
        }
    }

    static final int W = 26;

    public int[] minOperationsQueriesIII(int n, int[][] edges, int[][] queries) {
        Map<Integer, Integer>[] neighbors = new Map[n]; // 邻居节点Map数组，数组下标为边的一个顶点，key为边的另一个顶点，value为权重
        for (int i = 0; i < n; i++) {
            neighbors[i] = new HashMap<Integer, Integer>();
        }
        // 初始化数组 neighbors
        for (int[] edge : edges) {
            neighbors[edge[0]].put(edge[1], edge[2]);
            neighbors[edge[1]].put(edge[0], edge[2]);
        }
        List<int[]>[] queryArr = new List[n]; // 查询数组，数组下标为边的一个顶点，数组元素是一个列表，列表的第一个元素是另一个顶点，第二个元素是查询下标
        for (int i = 0; i < n; i++) {
            queryArr[i] = new ArrayList<int[]>();
        }
        int m = queries.length;
        for (int i = 0; i < m; i++) {
            queryArr[queries[i][0]].add(new int[]{queries[i][1], i});
            queryArr[queries[i][1]].add(new int[]{queries[i][0], i});
        }

        int[][] count = new int[n][W + 1];
        boolean[] visited = new boolean[n];
        int[] uf = new int[n];
        int[] lca = new int[m];
        tarjan(0, -1, neighbors, queryArr, count, visited, uf, lca);
        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int totalCount = 0, maxCount = 0;
            for (int j = 1; j <= W; j++) {
                int t = count[queries[i][0]][j] + count[queries[i][1]][j] - 2 * count[lca[i]][j];
                maxCount = Math.max(maxCount, t);
                totalCount += t;
            }
            res[i] = totalCount - maxCount;
        }
        return res;
    }

    public void tarjan(int node, int parent, Map<Integer, Integer>[] neighbors, List<int[]>[] queryArr, int[][] count, boolean[] visited, int[] uf, int[] lca) {
        if (parent != -1) {
            System.arraycopy(count[parent], 0, count[node], 0, W + 1);
            count[node][neighbors[node].get(parent)]++;
        }
        uf[node] = node;
        for (int child : neighbors[node].keySet()) {
            if (child == parent) {
                continue;
            }
            tarjan(child, node, neighbors, queryArr, count, visited, uf, lca);
            uf[child] = node;
        }
        for (int[] pair : queryArr[node]) {
            int node1 = pair[0], index = pair[1];
            if (node != node1 && !visited[node1]) {
                continue;
            }
            lca[index] = find(uf, node1);
        }
        visited[node] = true;
    }

    public int find(int[] uf, int i) {
        if (uf[i] == i) {
            return i;
        }
        uf[i] = find(uf, uf[i]);
        return uf[i];
    }

}
