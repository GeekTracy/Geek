package com.geek.tracy.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Collectors;


/**
 * 合集
 *
 * @Author Tracy
 * @Date 2023/6/26
 */
public class CodeSets {

    @Test
    public void test_2766() {
        // 输入：nums = [1,6,7,8], moveFrom = [1,7,2], moveTo = [2,9,5]
        //输出：[5,6,8,9]
        List<Integer> ans = relocateMarbles(new int[]{1, 6, 7, 8}, new int[]{1, 7, 2}, new int[]{2, 9, 5});
        System.out.println(ans);
    }

    /**
     * 2766.重新放置石块
     */
    public List<Integer> relocateMarbles(int[] nums, int[] moveFrom, int[] moveTo) {
        Set<Integer> numSet = Arrays.stream(nums).boxed().collect(Collectors.toSet());
        // 开始移动石头
        for (int i = 0; i < moveFrom.length; i++) {
            numSet.remove(moveFrom[i]);
            numSet.add(moveTo[i]);
        }
        ArrayList<Integer> ansList = new ArrayList<>(numSet);
        Collections.sort(ansList);
        return ansList;

    }


    /**
     * 1186. Maximum Subarray Sum with One Deletion 删除一次得到子数组最大和
     * <p>
     * Kadane's Algorithm思想求解，解决最大子数组和问题（最大子数组表示连续的最大子数组）
     */
    public int maximumSum(int[] arr) {
        // 最大子数组，且可删除一个元素，利用Kadane算法思想，额外定义一个数组，从i节点开始的数组startFromHere表示i节点开始的最大值
        int length = arr.length;
        int[] endHereMax = new int[length];
        int max = arr[0]; // 记录不删除子数组元素时的
        // Kadane算法得到endHereMax，endHereMax[i]的值为endHereMax[i-1]+arr[i]、arr[i]中较大的值（由其特性决定，子数组为连续的子数组）
        endHereMax[0] = arr[0];
        for (int i = 1; i < length; i++) {
            endHereMax[i] = Math.max(endHereMax[i - 1] + arr[i], arr[i]);
            max = Math.max(max, endHereMax[i]);
        }

        int[] startHereMax = new int[length];
        startHereMax[length - 1] = arr[length - 1];
        for (int i = length - 2; i >= 0; i--) {
            startHereMax[i] = Math.max(arr[i] + startHereMax[i + 1], arr[i]);
        }

        // endHerMax[i-1]+startHereMax[i+1] 表示删除了arr[i]值，因为可以自由决定删或不删，则将通过卡登算法得到的max与其比较大小即可
        for (int i = 1; i < length - 1; i++) {
            max = Math.max(max, startHereMax[i + 1] + endHereMax[i - 1]);
        }
        return max;

    }

    @Test
    public void test_2850() {
//        Assert.assertEquals(3, minimumMoves(new int[][]{{1, 1, 0}, {1, 1, 1}, {1, 2, 1}}));
        Assert.assertEquals(4, minimumMoves(new int[][]{{1, 3, 0}, {1, 0, 0}, {1, 0, 3}}));
    }

    private List<List<Integer>> allPermutation(int[] arr) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> row = new ArrayList<>();
        backSource(arr, ans, row);
        return ans;
    }

    private void backSource(int[] nums, List<List<Integer>> list, List<Integer> curr) {
        if (curr.size() == nums.length) {
            list.add(new ArrayList<>(curr));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            // 剪枝
            if (temp == Integer.MAX_VALUE) {
                continue;
            }
            nums[i] = Integer.MAX_VALUE;
            curr.add(temp);
            backSource(nums, list, curr);
            curr.remove(curr.size() - 1);
            nums[i] = temp;
        }
    }

    /**
     * 2850.将石头分散到网格图的最少移动次数
     */
    public int minimumMoves(int[][] grid) {
        List<List<Integer>> zeroArr = new ArrayList<>();
        List<List<Integer>> biggerArr = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                List<Integer> item = new ArrayList<>();
                item.add(i);
                item.add(j);
                if (grid[i][j] == 0) {
                    zeroArr.add(item);
                } else if (grid[i][j] > 1) {
                    for (int loop = 1; loop < grid[i][j]; loop++) {
                        biggerArr.add(item);
                    }
                }
            }
        }
        int moveNum = Integer.MAX_VALUE;
        // 数组zeroArr、biggerArr 分别按照i+j进行排序
        int[] arr = new int[zeroArr.size()];
        for (int i = 0; i < zeroArr.size(); i++) {
            arr[i] = i;
        }
        List<List<Integer>> indexPermutation = allPermutation(arr);
        for (List<Integer> indexList : indexPermutation) {
            int currMoves = 0;
            for (int i = 0; i < indexList.size(); i++) {
                int index = indexList.get(i);
                List<Integer> cur = biggerArr.get(i);
                List<Integer> zeroPoint = zeroArr.get(index);
                currMoves += Math.abs(zeroPoint.get(0) - cur.get(0)) + Math.abs(zeroPoint.get(1) - cur.get(1));
            }
            moveNum = Math.min(moveNum, currMoves);
        }
        return moveNum;
    }

    /**
     * 2956.找到两个数组中的公共元素
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] findIntersectionValues(int[] nums1, int[] nums2) {
        Set<Integer> set1 = Arrays.stream(nums1).boxed().collect(Collectors.toSet());
        Set<Integer> set2 = Arrays.stream(nums2).boxed().collect(Collectors.toSet());
        long ans1 = Arrays.stream(nums1).filter(item -> set2.contains(item)).count();
        long ans2 = Arrays.stream(nums2).filter(item -> set1.contains(item)).count();
        return new int[]{(int) ans1, (int) ans2};
    }

    /**
     * 721.账户合并  哈希表+dfs
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // 哈希集合，保存邮箱与accounts下表
        Map<String, List<Integer>> emailToIndexMap = new HashMap<>();
        for (int i = 0; i < accounts.size(); i++) {
            // 从下标为1开始遍历，第0个元素为用户名字
            for (int j = 1; j < accounts.get(i).size(); j++) {
                emailToIndexMap.computeIfAbsent(accounts.get(i).get(j), k -> new ArrayList<>()).add(i);
            }
        }

        Set<String> emailSet = new HashSet<>();  // 保存合并后的email账号集合
        boolean[] visit = new boolean[accounts.size()]; // 标记访问过的行
        List<List<String>> ans = new ArrayList<>();
        // 遍历访问每一行数据
        for (int i = 0; i < accounts.size(); i++) {
            if (visit[i]) {
                continue;
            }
            emailSet.clear();
            // 遍历当前行，且根据emailToIndexMap找到有相同账号的其他行，并将邮箱进行合并
            dfs(i, accounts, emailSet, visit, emailToIndexMap);
            List<String> emails = new ArrayList<>(emailSet);
            Collections.sort(emails);
            List<String> rowData = new ArrayList<>();
            rowData.add(accounts.get(i).get(0));
            rowData.addAll(emails);
            ans.add(rowData);
        }
        return ans;
    }

    private void dfs(int i, List<List<String>> accounts, Set<String> emailSet, boolean[] visit, Map<String, List<Integer>> emailToIndexMap) {
        visit[i] = true;
        for (int n = 1; n < accounts.get(i).size(); n++) {
            if (emailSet.contains(accounts.get(i).get(n))) {
                continue;
            }
            emailSet.add(accounts.get(i).get(n));
            if (emailToIndexMap.get(accounts.get(i).get(n)).size() > 1) {
                emailToIndexMap.get(accounts.get(i).get(n)).stream().filter(index -> !visit[index]).forEach(index -> dfs(index, accounts, emailSet, visit, emailToIndexMap));
            }
        }
    }

    /**
     * 807.保持城市天际线
     *
     * @param grid
     * @return
     */
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        // 寻找横向、纵向最大值数组colMax,rowMax
        int n = grid.length;
        int m = grid[0].length;
        int[] rowMax = new int[n];
        int[] colMax = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rowMax[i] = Math.max(rowMax[i], grid[i][j]);
                colMax[j] = Math.max(colMax[j], grid[i][j]);
            }
        }
        int maxIncrease = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maxIncrease += Math.min(rowMax[i], colMax[j]) - grid[i][j];
            }
        }
        return maxIncrease;

    }

    @Test
    public void test_3011() {
        int[] nums = {3, 16, 8, 4, 2};
        canSortArray(nums);
    }

    /**
     * 3011.判断一个数组是否可以变成有序
     *
     * @param nums
     * @return
     */
    public boolean canSortArray(int[] nums) {
        // 计算素组中二进制的个数
        int len = nums.length;
        int[] bitArr = new int[len];
        for (int i = 0; i < len; i++) {
            bitArr[i] = Integer.bitCount(nums[i]);
        }
        for (int i = 0; i < len; ) {
            int fromIndex = i;
            int toIndex = i;
            while (toIndex + 1 < len && bitArr[fromIndex] == bitArr[toIndex + 1]) {
                toIndex++;
            }
            if (fromIndex != toIndex) {
                Arrays.sort(nums, fromIndex, toIndex + 1);
            }
            i = toIndex + 1;
        }
        for (int i = 1; i < len; i++) {
            if (nums[i] < nums[i - 1]) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test_2974() {
        Assert.assertArrayEquals(new int[]{3, 2, 5, 4}, numberGame(new int[]{5, 4, 2, 3}));
        Assert.assertArrayEquals(new int[]{5, 2}, numberGame(new int[]{2, 5}));
    }

    /**
     * 2974. 最小数字游戏
     */
    public int[] numberGame(int[] nums) {
        int len = nums.length;
        int[] arr = new int[len];
        Arrays.sort(nums);
        for (int i = 0; i < len; i = i + 2) {
            arr[i] = nums[i + 1];
            arr[i + 1] = nums[i];
        }
        return arr;
    }


    @Test
    public void test_724() {
        Assert.assertEquals(3, pivotIndex(new int[]{1, 7, 3, 6, 5, 6}));
        Assert.assertEquals(-1, pivotIndex(new int[]{1, 2, 3}));
        Assert.assertEquals(0, pivotIndex(new int[]{2, 1, -1}));
    }

    /**
     * 724.寻找数组的中心下标
     * <p>
     * 提示：
     * <p>
     * 1 <= nums.length <= 104
     * -1000 <= nums[i] <= 1000
     */
    public int pivotIndex(int[] nums) {
        if (nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return 0;
        }
        int sum = Arrays.stream(nums).sum();
        int leftSum = 0;
        int pivot;
        for (int i = 0; i < nums.length; i++) {
            pivot = i;
            if (pivot - 1 >= 0) {
                leftSum += nums[pivot - 1];
            }
            int rightSum = sum - nums[pivot] - leftSum;
            if (leftSum == rightSum) {
                return pivot;
            }
        }
        return -1;
    }

    @Test
    public void test_2981() {
        // 输入：s = "aaaa"
        //输出：2
        //解释：出现三次的最长特殊子字符串是 "aa" ：子字符串 "aaaa"、"aaaa" 和 "aaaa"。
        //可以证明最大长度是 2 。
        System.out.println(maximumLength("aaaa"));
        System.out.println(maximumLength("axxaaavwww"));
    }

    /**
     * 2981.找出出现至少三次的最长特殊子字符串I
     * <p>
     * 提示：
     * <p>
     * 3 <= s.length <= 50
     * s 仅由小写英文字母组成。
     */
    public int maximumLength(String s) {
        // 两个概念：1）最长子字符串，连续的子字符；2）至少出现3次，则最长字串长度小于size - 2；3）特殊串是相同的串
        if (s == null) {
            return -1;
        }
        char[] chars = s.toCharArray();
        if (chars.length < 3) {
            return -1;
        }
        int maxSub = -1;
        // 按字串长度遍历
        for (int subLen = 1; subLen <= chars.length - 2; subLen++) {
            sub2:
            for (int i = 0; i < chars.length - 2; i++) {
                int count = 1;
                // 字串：从i到i+subLen
                for (int j = i + 1; j + subLen <= chars.length; j++) {
                    if (s.substring(i, subLen + i).equals(s.substring(j, subLen + j))) {
                        count++;
                    }
                }
                if (count >= 3) {
                    maxSub = Math.max(maxSub, subLen);
                    break sub2;
                }
            }
        }
        return maxSub;
    }

    @Test
    public void test_2951() {
        // 输入：mountain = [2,4,4]
        //输出：[]

        // 输入：mountain = [1,4,3,8,5]
        //输出：[1,3]
    }

    /**
     * 2951.寻找峰值
     * <p>
     * 给你一个下标从 0 开始的数组 mountain 。你的任务是找出数组 mountain 中的所有 峰值。
     * <p>
     * 以数组形式返回给定数组中 峰值 的下标，顺序不限 。
     * <p>
     * 注意：
     * <p>
     * 峰值 是指一个严格大于其相邻元素的元素。
     * 数组的第一个和最后一个元素 不 是峰值。
     *
     * @param mountain
     * @return
     */
    public List<Integer> findPeaks(int[] mountain) {
        List<Integer> ans = new ArrayList<>();
        if (mountain == null || mountain.length <= 2) {
            return ans;
        }
        // 遍历mountain，去掉收尾值
        for (int i = 1; i < mountain.length - 1; i++) {
            if (mountain[i] > mountain[i - 1] && mountain[i] > mountain[i + 1]) {
                ans.add(i);
            }
        }
        return ans;
    }


    @Test
    public void test_2865() {
//        Assert.assertEquals(13, maximumSumOfHeightsI(Arrays.asList(5,3,4,1,1)));
        Assert.assertEquals(22, maximumSumOfHeightsI(Arrays.asList(6, 5, 3, 9, 2, 7)));
        Assert.assertEquals(18, maximumSumOfHeightsI(Arrays.asList(3, 2, 5, 5, 2, 3)));
    }

    /**
     * 2865.美丽塔 Ⅰ
     * 存在一个峰顶下标i，其左右两边分别递增、递减
     *
     * @param maxHeights
     * @return
     */
    public long maximumSumOfHeightsI(List<Integer> maxHeights) {
        // 遍历整个数组，计算每个下标index为峰顶时，最大美丽塔数组的值
        if (maxHeights.size() <= 2) {
            return maxHeights.stream().mapToLong(Long::valueOf).sum();
        }
        int size = maxHeights.size();
        long res = 0;
        for (int i = 0; i < size; i++) {
            long sum = maxHeights.get(i);
            // 从 i --> 0，递减
            Integer leftMin = maxHeights.get(i);
            Integer rightMin = maxHeights.get(i);
            for (int left = i - 1; left >= 0; left--) {
                sum += maxHeights.get(left);
                leftMin = Math.min(leftMin, maxHeights.get(left));
                if (maxHeights.get(left) > leftMin) {
                    sum -= maxHeights.get(left) - leftMin;
                }
            }
            // 从 i --> size，递减
            for (int right = i + 1; right < size; right++) {
                sum += maxHeights.get(right);
                rightMin = Math.min(rightMin, maxHeights.get(right));
                if (maxHeights.get(right) > rightMin) {
                    sum -= maxHeights.get(right) - rightMin;
                }
            }
            res = Math.max(sum, res);
        }
        return res;
    }

    @Test
    public void test_2866() {
        List<Integer> list = new ArrayList<>();

    }

    /**
     * 2866.美丽塔 Ⅱ
     * 范围增大，暴力解法超时，需要使用单调栈
     *
     * @param maxHeights
     * @return
     */
    public long maximumSumOfHeightsII(List<Integer> maxHeights) {
        int[] a = maxHeights.stream().mapToInt(i -> i).toArray();
        int n = a.length;
        long[] suf = new long[n + 1];
        Deque<Integer> st = new ArrayDeque<Integer>();
        st.push(n); // 哨兵
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
        st.push(-1); // 哨兵
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
    public void test_1144() {
        movesToMakeZigzag(new int[]{7, 4, 8, 9, 7, 7, 5});
    }

    private void print(int[] array) {
        StringBuilder sb = new StringBuilder("[");
        for (int t : array) {
            sb.append(t).append(",");
        }
        String res = sb.substring(0, sb.length() - 1);
        System.out.println(res + "]");
    }

    /**
     * 1144.递减元素是数组呈锯齿状
     * <p>
     * 题解说明：存在两种锯齿，偶数下标或奇数下标为齿峰，不管哪一种，齿峰值不变。
     * <p>
     * * 以偶下标为齿峰为例：则nums[0]、nums[2]、nums[4]...不变，改变奇数下标nums[1]、nums[3]、nums[5]使其成为齿谷。
     * * 若下标i为要改变为峰谷的数，则需要改动的次数为：nums[i - 1]和nums[i + 1]中较小的为：min= min(nums[i - 1], nums[i + 1])，
     * * 要移动的次数为：count = num[i] - min + 1 次，则又可优化为：count = max(num[i] - min + 1, 0)次.(说明：小于0不需要修改，即count=0)
     * <p>
     * 此解法秒在：边界值的处理，常规表达式为：count = max(nums[i] - min(nums[i - 1], nums[i + 1]) + 1, 0)，本题边界值nums[i - 1]和nums[i + 1]
     * 的处理，由于对其二者是取min，所以将边界值设定为Integer.MAX，即可将边界值常规化处理
     */
    public int movesToMakeZigzag(int[] nums) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int left = i - 1 < 0 ? Integer.MAX_VALUE : nums[i - 1];
            int right = i + 1 > nums.length - 1 ? Integer.MAX_VALUE : nums[i + 1];
            int count = Math.max(nums[i] - Math.min(left, right) + 1, 0);
            res[i % 2] += count;
        }
        return Math.min(res[0], res[1]);
    }


    @Test
    public void test_1901() {
        Assert.assertEquals(new int[]{0, 1}, findPeakGrid(new int[][]{{1, 4}, {3, 2}}));
    }


    /**
     * 1901.寻找峰值Ⅱ
     *
     * @param mat
     * @return
     */
    public int[] findPeakGrid(int[][] mat) {
        int row = mat.length;
        int col = mat[0].length;
        int[][] temp = new int[row + 2][col + 2];
        for (int[] ints : temp) {
            Arrays.fill(ints, -1);
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                temp[i + 1][j + 1] = mat[i][j];
            }
        }
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                if (temp[i][j] > temp[i][j - 1]
                        && temp[i][j] > temp[i][j + 1]
                        && temp[i][j] > temp[i - 1][j]
                        && temp[i][j] > temp[i + 1][j]) {
                    return new int[]{i - 1, j - 1};
                }
            }
        }
        return new int[0];
    }


    @Test
    public void test_2697() {
    }

    /**
     * 2697. 字典序最小回文串
     *
     * @param s
     * @return
     */
    public String makeSmallestPalindrome(String s) {
        char[] chars = s.toCharArray();
        int start = 0;
        int end = chars.length - 1;
        while (start < end) {
            if (chars[start] < chars[end]) {
                chars[end] = chars[start];
            } else if (chars[start] > chars[end]) {
                chars[start] = chars[end];
            }
            start++;
            end--;
        }
        return new String(chars);
    }

    @Test
    public void test_009() {
        System.out.println(isPalindrome(123));
        System.out.println(isPalindrome(1221));
        System.out.println(isPalindrome(12321));
    }

    /**
     * 9. 回文数 -- 数学法
     *
     * @param x
     * @return
     */
    public boolean isPalindrome_01(int x) {
        if (x < 0) {
            return false;
        }
        int temp = x;
        int trans = 0;
        while (temp != 0) {
            trans = trans * 10 + temp % 10;
            temp = temp / 10;
        }
        return trans == x;
    }


    /**
     * 9. 回文数 -- 字符串比对法
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        char[] chars = String.valueOf(x).toCharArray();
        int start = 0;
        int end = chars.length - 1;
        while (start < end) {
            if (chars[start] != chars[end]) {
                return false;
            } else {
                start++;
                end--;
            }
        }
        return true;
    }

    @Test
    public void test_012() {
        Assert.assertEquals("III", intToRoman(3));
        Assert.assertEquals("LVIII", intToRoman(58));
        Assert.assertEquals("MCMXCIV", intToRoman(1994));
    }

    /**
     * 12.整数转罗马数字
     * 罗马数字包含：I V X L C D M
     * 对应关系如下：
     * I   1
     * V   5
     * X   10
     * L   50
     * C   100
     * D   500
     * M   1000
     * 说明：通常小数字在大数字的右边。单存在特例，例如：IV : 4,IX ：9，同理还有：XL : 40, XC : 90, CD : 400, CM : 900
     *
     * @param num 1 <= num <= 3999
     */
    public String intToRoman(int num) {
        // 贪心算法，构建映射关系，从最大依次往下匹配
        List<Integer> intList = Arrays.asList(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1);
        List<String> romanList = Arrays.asList("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I");

        int size = intList.size();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            while (num >= intList.get(i)) {
                result.append(romanList.get(i));
                num -= intList.get(i);
            }
        }
        return result.toString();
    }

    @Test
    public void test_013() {
        Assert.assertEquals(58, romanToInt("LVIII"));
    }

    /**
     * 13. 罗马数字转整数
     */
    public int romanToInt(String s) {
        Map<Character, Integer> dictionary = new HashMap<>();
        dictionary.put('I', 1);
        dictionary.put('V', 5);
        dictionary.put('X', 10);
        dictionary.put('L', 50);
        dictionary.put('C', 100);
        dictionary.put('D', 500);
        dictionary.put('M', 1000);

        char[] chars = s.toCharArray();
        int result = 0;
        int index = 0;
        while (index < chars.length) {
            if (index + 1 < chars.length && dictionary.get(chars[index]) < dictionary.get(chars[index + 1])) {
                result -= dictionary.get(chars[index]);
            } else {
                result += dictionary.get(chars[index]);
            }
            index++;
        }
        return result;
    }

    @Test
    public void test_014() {
        System.out.println(longestCommonPrefix(new String[]{"longest", "long", "lon"}));
        System.out.println(longestCommonPrefix(new String[]{"longest", "longestdistance", "longestway"}));
    }

    /**
     * 14.最长公共前缀
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        char[] res = strs[0].toCharArray();
        for (int index = 0; index < res.length; index++) {
            for (int i = 1; i < strs.length; i++) {
                if (index == strs[i].length() || res[index] != strs[i].charAt(index)) {
                    return strs[0].substring(0, index);
                }
            }
        }
        return strs[0];
    }

    @Test
    public void testMessageFormat() {

        System.out.println(MessageFormat.format("My '{'name} is {0}", "joe"));
        System.out.println(MessageFormat.format("My ''name'' is {0}", "joe"));
    }

    @Test
    public void suceessfulPares() {
        String ss = "来看据了解{id}了建立起而空气为了领取{billno}我今儿两千万人";
        char[] chars = ss.toCharArray();
        List<String> properties = new ArrayList<>();
        for (int i = 0; i < ss.length(); i++) {
            if (chars[i] == '{') {
                int start = i + 1;
                int end = 0;
                while (i < ss.length() && chars[i] != '}') {
                    i++;
                }
                end = i;
                properties.add(ss.substring(start, end));
                System.out.println(ss.substring(start, end));
            }
        }
        System.out.println(ss);
        ss = ss.replace("{id}", "12341234");
        ss = ss.replace("{billno}", "NO1314520");
        System.out.println(ss);
    }

    /**
     * 2127. 参加会议的最多员工数
     */
    public int maximumInvitations(int[] favorite) {
        return 0;
    }

    /**
     * 2300. 咒语和药水的成功对数
     */
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        int m = potions.length;
        int[] pairs = new int[spells.length];
        for (int i = 0; i < spells.length; i++) {
            int spell = spells[i];
            int count = 0;
            for (int potion : potions) {
                if ((long) spell * potion < success) {
                    count++;
                } else {
                    break;
                }
            }
            pairs[i] = m - count;
        }
        return pairs;
    }

    /**
     * 2586. 统计范围内的元音字符串数
     */
    public int vowelStrings(String[] words, int left, int right) {
        List<Character> list = Arrays.asList('a', 'e', 'i', 'o', 'u');
        int sum = 0;
        for (int i = left; i < right; i++) {
            String word = words[i];
            if (list.contains(word.charAt(0)) && list.contains(word.charAt(word.length() - 1))) {
                sum++;
            }
        }
        return sum;
    }

    @Test
    public void maxtProductTest() {
        String[] words = Arrays.asList("abcw", "baz", "foo", "bar", "xtfn", "abcdef").toArray(new String[0]);
        System.out.println(maxProduct(words));
    }

    /**
     * 318. 最大单词长度乘积
     * 分析：只有小写字母，可以利用 ‘&’ ‘|’ 与 非运算判断，先将单词数组进行预处理，26位的二进制位，有哪个字母，那一位标志为：1，初始化完所有
     * 单词后，word1 & word2 = 0，即标识2个单词没有重复字母！
     */
    public int maxProduct(String[] words) {
        int max = 0;
        int[] intiBinary = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            char[] chars = words[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                intiBinary[i] = intiBinary[i] | 1 << ((chars[j]) - 'a');
            }
        }
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if ((intiBinary[i] & intiBinary[j]) == 0) {
                    max = Math.max(max, words[i].length() * words[j].length());
                }
            }
        }
        return max;
    }

    @Test
    public void countPointsTest() {
        System.out.println(countPoints("B7R5B3G5B1R2B8"));
    }

    /**
     * 2103. 环和杆
     */
    public int countPoints(String rings) {
        // 初始化3个颜色，二进制位111 代表各个位置受否有对应颜色
        Map<Character, Integer> map = new HashMap<>();
        map.put('R', 1);
        map.put('G', 2);
        map.put('B', 4);
        int[] colorIn = new int[10];
        char[] chars = rings.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char cha = chars[i++];
            colorIn[Integer.parseInt(String.valueOf(chars[i]))] |= map.get(cha);
        }
        int sum = 0;
        for (int i : colorIn) {
            if (i == 7) {
                sum++;
            }
        }
        return sum;
    }

    @Test
    public void insertTest() {
        // 示例 1：
        //
        //输入：intervals = [[1,3],[6,9]], newInterval = [2,5]
        //输出：[[1,5],[6,9]]
        //示例 2：
        //
        //输入：intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
        //输出：[[1,2],[3,10],[12,16]]
        //解释：这是因为新的区间 [4,8] 与 [3,5],[6,7],[8,10] 重叠。
        //示例 3：
        //
        //输入：intervals = [], newInterval = [5,7]
        //输出：[[5,7]]
        //示例 4：
        //
        //输入：intervals = [[1,5]], newInterval = [2,3]
        //输出：[[1,5]]
        //示例 5：
        //
        //输入：intervals = [[1,5]], newInterval = [2,7]
        //输出：[[1,7]]
    }

    /**
     * 57. 插入区间
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0) {
            return new int[][]{newInterval};
        }
        List<int[]> list = new ArrayList<>();
        return null;
    }


    @Test
    public void countServersTest() {
//        Assert.assertEquals(0, countServers(new int[][]{{1,0}, {0,1}}));
        Assert.assertEquals(3, countServers(new int[][]{{1, 0}, {1, 1}}));
//        Assert.assertEquals(4, countServers(new int[][]{{1,1,0,0}, {0,0,1,0},{0,0,1,0},{0,0,0,1}}));
    }

    /**
     * 1267. 统计参与通信的服务器
     */
    public int countServers(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int sum = 0;
        int[] rowFlagArr = new int[row];
        int[] colFlagArr = new int[col];

        for (int i = 0; i < row; i++) {
            int count = 0;
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 1) {
                    count++;
                }
                if (count == 2) {
                    rowFlagArr[i] = 1;
                    break;
                }
            }
        }

        for (int i = 0; i < col; i++) {
            int count = 0;
            for (int j = 0; j < row; j++) {
                if (grid[j][i] == 1) {
                    count++;
                }
                if (count == 2) {
                    colFlagArr[i] = 1;
                    break;
                }
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 0) continue;
                // 行、列标记为1，则该节点存在可通信节点
                if (rowFlagArr[i] == 1 || colFlagArr[j] == 1) {
                    sum++;
                    continue;
                }
            }
        }
        return sum;
    }

    @Test
    public void maxDistToClosestTest() {
        Assert.assertEquals("{1, 0, 0, 0, 1, 0, 1} 应该为：2", 2, maxDistToClosest(new int[]{1, 0, 0, 0, 1, 0, 1}));
        Assert.assertEquals("{1,0,0,0} 应该为：3", 3, maxDistToClosest(new int[]{1, 0, 0, 0}));
        Assert.assertEquals("{0,1} 应该为：1", 1, maxDistToClosest(new int[]{0, 1}));
    }

    /**
     * 849. 到最近的人的最大距离
     * <p>
     * 提示：
     * 2 <= seats.length <= 2 * 104
     * seats[i] 为 0 或 1
     * 至少有一个 空座位
     * 至少有一个 座位上有人
     */
    public int maxDistToClosest(int[] seats) {
        List<Integer> peopleIndex = new ArrayList<>();
        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == 1) {
                peopleIndex.add(i);
            }
        }
        int max = 0;
        if (peopleIndex.get(0) != 0) {
            max = Math.max(peopleIndex.get(0), max);
        }
        if (peopleIndex.get(peopleIndex.size() - 1) != seats.length - 1) {
            max = Math.max(seats.length - 1 - peopleIndex.get(peopleIndex.size() - 1), max);
        }
        for (int i = 1; i < peopleIndex.size(); i++) {
            max = Math.max((peopleIndex.get(i) - peopleIndex.get(i - 1)) / 2, max);
        }
        return max;
    }


    /**
     * 2337. 移动片段得到字符串
     */
    public boolean canChange(String start, String target) {
        // 定义两个指针i,j，分别遍历start，target，出去“_”之外，遇见L，i>=j，遇见R，i<=j，否则返回false
        int i = 0;
        int j = 0;
        int n = start.length();
        if (!start.replaceAll("_", "").equals(target.replace("_", ""))) {
            return false;
        }
        while (i < n || j < n) {
            while (i < n && start.charAt(i) == '_') {
                i++;
            }
            while (j < n && target.charAt(j) == '_') {
                j++;
            }
            if (i == n && j == n) {
                return true;
            }
            if (start.charAt(i) != target.charAt(j)) {
                return false;
            }
            if (start.charAt(i) == 'L' && i < j || start.charAt(i) == 'R' && i > j) {
                return false;
            }
            i++;
            j++;
        }
        return true;
    }


    @Test
    public void mergeTest() {
        int[] nums1 = new int[]{1, 2, 3, 0, 0, 0};
        int[] nums2 = new int[]{2, 5, 6};
        merge(nums1, 3, nums2, 3);

        int[] nums11 = new int[]{1};
        int[] nums22 = new int[]{};
        merge(nums11, 1, nums22, 0);

        int[] nums111 = new int[]{0};
        int[] nums222 = new int[]{1};
        merge(nums111, 0, nums222, 1);
        System.out.println();
    }

    /**
     * 88. 合并两个有序数组
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        while (m > 0 || n > 0) {
            if (m == 0) {
                nums1[n - 1] = nums2[n - 1];
                n--;
            } else if (n == 0) {
                m--;
            } else if (nums1[m - 1] > nums2[n - 1]) {
                nums1[m + n - 1] = nums1[m - 1];
                m--;
            } else {
                nums1[m + n - 1] = nums2[n - 1];
                n--;
            }
        }
    }

    /**
     * 2682. 找出转圈游戏输家
     */
    public int[] circularGameLosers(int n, int k) {
        int[] res = new int[n + 1];
        res[1] = 1;
        int index = 1;
        int i = 1;
        while (res[index] != 2) {
            index = (index + k * i) % n;
            i++;
            if (index == 0) {
                res[n] += 1;
                index = n;
            } else {
                res[index] += 1;
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int j = 1; j <= n; j++) {
            if (res[j] == 0) {
                result.add(j);
            }
        }
        System.out.println(result);
        int[] kk = new int[result.size()];
        for (int m = 0; m < result.size(); m++) {
            kk[m] = result.get(m);
        }
        return kk;
    }


    /**
     * 1572. 矩阵对角线元素的和
     */
    public int diagonalSum(int[][] mat) {
        // 分析法：根据矩阵边长分为奇数、偶数2种情形处理
        int n = mat.length;
        int count = 0;
        for (int i = 0, j = n - 1; i < n && j >= 0; i++, j--) {
            count += mat[i][i] + mat[i][j];
        }
        if ((n & 1) == 1) {
            // 奇数
            count -= mat[n / 2][n / 2];
        }
        return count;
    }

    @Test
    public void maxAbsoluteSumTest() {
        maxAbsoluteSum(new int[]{1, -3, 2, 3, -4});
    }

    /**
     * 1749. 任意子数组和的绝对值的最大值
     */
    public int maxAbsoluteSum(int[] nums) {
        int pre = nums[0];
        int maxHere = nums[0];
        int minHere = nums[0];
        // 遍历求最大
        for (int i = 1; i < nums.length; i++) {
            pre = Math.max(nums[i], pre + nums[i]);
            maxHere = Math.max(pre, maxHere);
        }
        // 遍历求最小
        pre = nums[0];
        for (int i = 1; i < nums.length; i++) {
            pre = Math.min(nums[i], pre + nums[i]);
            minHere = Math.min(pre, minHere);
        }
        return Math.max(Math.abs(maxHere), Math.abs(minHere));
    }

    /**
     * 344. 反转字符串
     */
    public void reverseString(char[] s) {
        int i = 0;
        int j = s.length - 1;
        while (i < j) {
            char temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            i++;
            j--;
        }
    }


    /**
     * 722. 删除注释
     */
    public List<String> removeComments(String[] source) {
        List<String> res = new ArrayList<String>();
        StringBuilder newLine = new StringBuilder();
        boolean inBlock = false;
        for (String line : source) {
            for (int i = 0; i < line.length(); i++) {
                if (inBlock) {
                    if (i + 1 < line.length() && line.charAt(i) == '*' && line.charAt(i + 1) == '/') {
                        inBlock = false;
                        i++;
                    }
                } else {
                    if (i + 1 < line.length() && line.charAt(i) == '/' && line.charAt(i + 1) == '*') {
                        inBlock = true;
                        i++;
                    } else if (i + 1 < line.length() && line.charAt(i) == '/' && line.charAt(i + 1) == '/') {
                        break;
                    } else {
                        newLine.append(line.charAt(i));
                    }
                }
            }
            if (!inBlock && newLine.length() > 0) {
                res.add(newLine.toString());
                newLine.setLength(0);
            }
        }
        return res;
    }

    /**
     * 822. 翻转卡片游戏
     * <p>
     * 输入：fronts = [1,2,4,4,7], backs = [1,3,4,1,3]
     * 输出：2
     * 解释：假设我们翻转第二张卡片，那么在正面的数变成了 [1,3,4,4,7] ， 背面的数变成了 [1,2,4,1,3]。
     * 接着我们选择第二张卡片，因为现在该卡片的背面的数是 2，2 与任意卡片上正面的数都不同，所以 2 就是我们想要的数字。
     * <p>
     * 提示：
     * n == fronts.length == backs.length
     * 1 <= n <= 1000
     * 1 <= fronts[i], backs[i] <= 2000
     */
    // 阅读理解，理解题意，正反相同的卡片，无论怎么翻转，都会出现现在正面，所以与之相同的不能被选中，其他的都有可能，找到最小的即可
    public int flipgame(int[] fronts, int[] backs) {
        Set<Integer> set = new HashSet<>();
        int n = fronts.length;
        for (int i = 0; i < n; i++) {
            if (fronts[i] == backs[i]) {
                set.add(fronts[i]);
            }
        }
        int min = 2001; // 数据范围：[1, 2000]，所以设置边界值为：2001
        for (int i = 0; i < n; i++) {
            if (!set.contains(fronts[i])) {
                min = Math.min(min, fronts[i]);
            }
            if (!set.contains(backs[i])) {
                min = Math.min(min, backs[i]);
            }
        }
        return min == 2001 ? 0 : min;
    }

    /**
     * 860. 柠檬水找零
     */
    public boolean lemonadeChange(int[] bills) {
        Map<Integer, Integer> hash = new HashMap<>();
        hash.putIfAbsent(5, 0);
        hash.putIfAbsent(10, 0);
        for (int bill : bills) {
            if (bill == 5) {
                hash.put(5, hash.get(5) + 1);
            } else if (bill == 10) {
                hash.put(10, hash.get(10) + 1);
                if (hash.get(5) == 0) {
                    return false;
                } else {
                    hash.put(5, hash.get(5) - 1);
                }
            } else {
                if (hash.get(10) == 0) {
                    if (hash.get(5) > 2) {
                        hash.put(5, hash.get(5) - 3);
                    } else {
                        return false;
                    }
                } else {
                    hash.put(10, hash.get(10) - 1);
                    if (hash.get(5) == 0) {
                        return false;
                    } else {
                        hash.put(5, hash.get(5) - 1);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 874. 模拟行走机器人 -- 测试
     */
    @Test
    public void robotSimTest() {
        // 示例 1：
        //
        //输入：commands = [4,-1,3], obstacles = []
        //输出：25
        //解释：
        //机器人开始位于 (0, 0)：
        //1. 向北移动 4 个单位，到达 (0, 4)
        //2. 右转
        //3. 向东移动 3 个单位，到达 (3, 4)
        //距离原点最远的是 (3, 4) ，距离为 3^2 + 4^2 = 25
        System.out.println(robotSim(new int[]{4, -1, 3}, new int[0][0]));

        //示例 2：
        //
        //输入：commands = [4,-1,4,-2,4], obstacles = [[2,4]]
        //输出：65
        //解释：机器人开始位于 (0, 0)：
        //1. 向北移动 4 个单位，到达 (0, 4)
        //2. 右转
        //3. 向东移动 1 个单位，然后被位于 (2, 4) 的障碍物阻挡，机器人停在 (1, 4)
        //4. 左转
        //5. 向北走 4 个单位，到达 (1, 8)
        //距离原点最远的是 (1, 8) ，距离为 1^2 + 8^2 = 65
        System.out.println(robotSim(new int[]{4, -1, 4, -2, 4}, new int[][]{{2, 4}}));

    }


    /**
     * 874. 模拟行走机器人
     * 机器人在一个无限大小的 XY 网格平面上行走，从点 (0, 0) 处开始出发，面向北方。该机器人可以接收以下三种类型的命令 commands ：
     * <p>
     * -2 ：向左转 90 度
     * -1 ：向右转 90 度
     * 1 <= x <= 9 ：向前移动 x 个单位长度
     * 在网格上有一些格子被视为障碍物 obstacles 。第 i 个障碍物位于网格点  obstacles[i] = (xi, yi) 。
     * <p>
     * 提示：
     * <p>
     * 1 <= commands.length <= 10^4
     * commands[i] is one of the values in the list [-2,-1,1,2,3,4,5,6,7,8,9].
     * 0 <= obstacles.length <= 10^4
     * -3 * 10^4 <= xi, yi <= 3 * 10^4
     * 答案保证小于 2^31
     * <p>
     * 解法分析：哈希表 + 模拟（行走方向）
     */
    public int robotSim(int[] commands, int[][] obstacles) {
        // 哈希表保存障碍数组，加速运算
        int direc = 0;
        // 当前x/y节点
        int curr_x = 0;
        int curr_y = 0;
        // 欧氏距离
        int distance = 0;
        // 由于obstacles[i] = (xi, yi)，其中：-3 * 10^4 <= xi, yi <= 3 * 10^4
        // 为了将obstacles二维矩阵转为哈希表，取obstacles二维数组的最大值覆盖所有可能的xi/yi，即元素xi/yi取值区间为：
        // [-3 * 10^4) ， (3 * 10^4] --> [-30000, 30000],
        // 则obstacles数组的空间为：60001 * 60001，再把矩阵一维化（第二行接到第一行后面，以此类推），则obstacles[x][y]对应哈希表
        // 值为：x * 60001 + y
        Set<Integer> intSet = new HashSet<>();
        for (int[] obstacle : obstacles) {
            intSet.add(obstacle[0] * 60001 + obstacle[1]);
        }
        for (int i = 0; i < commands.length; i++) {
            int command = commands[i];
            if (command < 0) {
                if (command == -2) {
                    // 左转
                    direc++;
                } else if (command == -1) {
                    // 右转
                    direc--;
                }
                // 保持direc始终在数组direction中
                direc = (direc + 4) % 4;
            } else {
                // 定义数组：0, 1, 2, 3 分别表示  ↑ ← ↓ →，则初始方向值为：0，左转++，右转--
                switch (direc) {
                    case 0:
                        // 上
                        for (int j = 0; j < command; j++) {
                            if (intSet.contains(curr_x * 60001 + (curr_y + 1))) {
                                break;
                            } else {
                                curr_y++;
                            }
                        }
                        distance = Math.max(distance, curr_x * curr_x + curr_y * curr_y);
                        break;
                    case 1:
                        // 左
                        for (int j = 0; j < command; j++) {
                            if (intSet.contains((curr_x - 1) * 60001 + curr_y)) {
                                break;
                            } else {
                                curr_x--;
                            }
                        }
                        distance = Math.max(distance, curr_x * curr_x + curr_y * curr_y);
                        break;
                    case 2:
                        // 下
                        for (int j = 0; j < command; j++) {
                            if (intSet.contains(curr_x * 60001 + (curr_y - 1))) {
                                break;
                            } else {
                                curr_y--;
                            }
                        }
                        distance = Math.max(distance, curr_x * curr_x + curr_y * curr_y);
                        break;
                    case 3:
                        // 右
                        for (int j = 0; j < command; j++) {
                            if (intSet.contains((curr_x + 1) * 60001 + curr_y)) {
                                break;
                            } else {
                                curr_x++;
                            }
                        }
                        distance = Math.max(distance, curr_x * curr_x + curr_y * curr_y);
                        break;
                    default:
                        break;
                }
            }
        }
        return distance;
    }

    /**
     * 2544. 交替数字和 -- 测试
     */
    public void alternateDigitSumTest() {
        System.out.println(alternateDigitSum(4));
        System.out.println(alternateDigitSum(12));
        System.out.println(alternateDigitSum(123));
        System.out.println(alternateDigitSum(6789));
    }

    /**
     * 2544. 交替数字和
     * <p>
     * 给你一个正整数 n 。n 中的每一位数字都会按下述规则分配一个符号：
     * <p>
     * 最高有效位 上的数字分配到 正 号。
     * 剩余每位上数字的符号都与其相邻数字相反。
     * 返回所有数字及其对应符号的和。
     */
    public int alternateDigitSum(int n) {
        // 位数
        int num = 0;
        int sum = 0;
        while (n > 0) {
            sum += n % 10 * Math.pow(-1, num);
            n = n / 10;
            num++;
        }
        // 位数为偶数，取相反数
        if ((num & 1) == 0) {
            sum = -1 * sum;
        }
        return sum;
    }


    @Test
    public void threeSumClosestTest() {
//        System.out.println(threeSumClosest(new int[]{-1,2,1,-4}, 1));
        System.out.println(threeSumClosest(new int[]{0, 1, 2}, 3));
    }

    /**
     * 16. 最接近的三数之和
     * 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
     * 返回这三个数的和。假定每组输入只存在恰好一个解。
     * <p>
     * 分析：排序 + 双指针法，类似：15. 三数之和，差异点：15题和为0，此题和接近target
     */
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        if (nums.length == 3) {
            return nums[0] + nums[1] + nums[2];
        }
        // 排序
        Arrays.sort(nums);
        int res = 0;
        int sub = Integer.MAX_VALUE;
        // 遍历数组的元素，由于求的是和为0，又数组已排序
        for (int i = 0; i < nums.length; i++) {
            // 如果下标i的元素大于0，则3数之和不可能为0，终止循环
            // 本题存在重复元素，当nums[i] = nums[i - 1]，则遍历i时会出现重复结果，需要跳过
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 双指针，l、r一前一后，收缩遍历
            int l = i + 1;
            int r = nums.length - 1;

            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == target) {
                    return nums[i] + nums[l] + nums[r];
                } else if (sum < target) {
                    if (Math.abs(sum - target) < sub) {
                        sub = Math.abs(sum - target);
                        res = sum;
                    }
                    // 左指针，重复值，取最右
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    l++;
                } else {
                    if (Math.abs(sum - target) < sub) {
                        sub = Math.abs(sum - target);
                        res = sum;
                    }
                    // 右指针，重复值，取最左
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    r--;
                }
            }
        }
        return res;
    }

    @Test
    public void test01() {
        twoSum(new int[]{0, 0, 3, 4}, 0);
    }

    public int[] twoSum(int[] numbers, int target) {
        int size = numbers.length;
        int[] res = new int[2];
        for (int i = 0; i < size; i++) {
            for (int j = i + i; j < size; j++) {
                if (numbers[i] + numbers[j] == target) {
                    res[0] = i + 1;
                    res[1] = j + 1;
                    return res;
                }
            }
        }
        return res;
    }

    /**
     * 2679. 矩阵中的和
     * <p>
     * 给你一个下标从 0 开始的二维整数数组 nums 。一开始你的分数为 0 。你需要执行以下操作直到矩阵变为空：
     * <p>
     * 矩阵中每一行选取最大的一个数，并删除它。如果一行中有多个最大的数，选择任意一个并删除。
     * 在步骤 1 删除的所有数字中找到最大的一个数字，将它添加到你的 分数 中。
     * 请你返回最后的 分数 。
     */
    public int matrixSum(int[][] nums) {
        for (int[] num : nums) {
            Arrays.sort(num);
        }
        int score = 0;
        for (int col = 0; col < nums[0].length; col++) {
            int colMax = 0;
            for (int row = 0; row < nums.length; row++) {
                colMax = Math.max(nums[row][col], colMax);
            }
            score += colMax;
        }
        return score;
    }

    @Test
    public void maximumEvenSplitTest() {
        for (int i = 2; i < 50; i = i + 2) {
            System.out.println("finalSum:" + i + ": " + maximumEvenSplit(i));
        }
    }

    /**
     * 2178. 拆分成最多数目的正偶数之和
     */
    public List<Long> maximumEvenSplit(long finalSum) {
        if (finalSum % 2 != 0) {
            return new ArrayList<>();
        }
        List<Long> result = new ArrayList<>();
        long sum = 0;
        for (Long i = 2L; sum < finalSum; i = i + 2) {
            sum += i;
            result.add(i);
        }
        if (sum == finalSum) {
            return result;
        } else if (sum > finalSum) {
            sum -= result.get(result.size() - 1);
            result.remove(result.size() - 1);
            Long left = finalSum - sum;
            result.set(result.size() - 1, result.get(result.size() - 1) + left);
        }
        return result;
    }


    /**
     * 2490. 回环句
     */
    public boolean isCircularSentence(String sentence) {
        String[] sentenceArr = sentence.split(" ");
        if (!circularWord(sentenceArr[sentenceArr.length - 1], sentenceArr[0])) {
            return false;
        }
        for (int i = 1; i < sentenceArr.length; i++) {
            if (!circularWord(sentenceArr[i - 1], sentenceArr[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean circularWord(String word1, String word2) {
        char[] char1 = word1.toCharArray();
        char[] char2 = word2.toCharArray();
        return char1[char1.length - 1] == char2[0];
    }

    @Test
    public void reconstructMatrixTest() {
        System.out.println(reconstructMatrix(2, 1, new int[]{1, 1, 1}));
        System.out.println(reconstructMatrix(2, 3, new int[]{1, 1, 1,}));
        System.out.println(reconstructMatrix(5, 5, new int[]{2, 1, 2, 0, 1, 0, 1, 2, 0, 1}));
    }


    /**
     * 1253. 重构 2 行二进制矩阵
     */
    public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
        // 数组是二进制数组，则数组的元素不是0，就是1，在colsum数组中的元素只可能是：0、1、2，
        int size = colsum.length;
        // colsum数组中，值为2的个数
        List<Integer> twoIndex = new ArrayList<>();
        List<Integer> oneIndex = new ArrayList<>();

        int[] upperArr = new int[size];
        int[] lowerArr = new int[size];

        for (int i = 0; i < size; i++) {
            if (colsum[i] == 2) {
                twoIndex.add(i);
            } else if (colsum[i] == 1) {
                oneIndex.add(i);
            }
        }
        // 1) upper + lower 不等于 colusum的元素之和，则返回空数组
        if (upper + lower != twoIndex.size() * 2 + oneIndex.size()) {
            return new ArrayList<>();
        }
        // 2) upper/lower 的值都要大于2的个数，因为colsum元素为2时，即upper,lower对应的二位二进制数组对应下标的值均为1，所以 {upper,lower} >= 2的个数
        if (upper < twoIndex.size() || lower < twoIndex.size()) {
            return new ArrayList<>();
        }
        for (Integer index : twoIndex) {
            upperArr[index] = 1;
            lowerArr[index] = 1;
        }

        int oneCount = upper - twoIndex.size();
        for (Integer index : oneIndex) {
            if (oneCount-- > 0) {
                upperArr[index] = 1;
            } else {
                lowerArr[index] = 1;
            }
        }
        List<List<Integer>> result = new ArrayList<>();
        result.add(toList(upperArr));
        result.add(toList(lowerArr));
        return result;
    }

    private List<Integer> toList(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int j : arr) {
            list.add(j);
        }
        return list;
    }

    /**
     * 2485. 找出中枢整数 --测试
     */
    @Test
    public void pivotIntegerTest() {
        // 输入：n = 8，输出：6
        //解释：6 是中枢整数，因为 1 + 2 + 3 + 4 + 5 + 6 = 6 + 7 + 8 = 21 。
        System.out.println(pivotInteger(8));
        //输入：n = 1,输出：1
        //解释：1 是中枢整数，因为 1 = 1 。
//        System.out.println(pivotInteger(1));
        // 输入：n = 4,输出：-1
        //解释：可以证明不存在满足题目要求的整数。
        System.out.println(pivotInteger(4));
    }

    /**
     * 2485. 找出中枢整数
     * 提示：
     * 1 <= n <= 1000
     */
    public int pivotInteger(int n) {
        if (n == 1) {
            return 1;
        }
        // 总和
        int sum = (int) ((1 + n) * n / 2.0);
        // 游标平移，左侧和小于右侧，则右移，反之左移，游标取折半中点
        int left = 1;
        int right = n;
        while (left <= right) {
            int pivot = (left + right + 1) / 2;
            int leftSum = (1 + pivot) * pivot / 2;
            int rightSum = sum - leftSum + pivot;
            if (leftSum == rightSum) {
                return pivot;
            } else if (leftSum < rightSum) {
                left = pivot + 1;
            } else {
                right = pivot - 1;
            }
        }
        return -1;
    }

    /**
     * 415. 字符串相加 - 测试
     */
    @Test
    public void addStringsTest() {
        System.out.println(addStrings("99", "45"));
        System.out.println(addStrings("5987", "45"));
    }

    /**
     * 415. 字符串相加
     */
    public String addStrings(String num1, String num2) {
        int len1 = num1.length() - 1;
        int len2 = num2.length() - 1;
        char[] arr1 = num1.toCharArray();
        char[] arr2 = num2.toCharArray();
        StringBuilder sb = new StringBuilder("");
        boolean flag = false;
        while (len1 >= 0 || len2 >= 0) {
            int sum = 0;
            if (len1 >= 0) {
                char a1 = arr1[len1--];
                sum += a1 - '0';
            }
            if (len2 >= 0) {
                char a2 = arr2[len2--];
                sum += a2 - '0';
            }
            if (flag) {
                sum++;
            }
            if (sum > 9) {
                flag = true;
                sum %= 10;
            } else {
                flag = false;
            }
            sb.append(sum);
        }
        if (flag) {
            sb.append("1");
        }
        sb.reverse();
        return sb.toString();
    }

    /**
     * 66. 加一 --测试
     */
    @Test
    public void plusOneTest() {
        System.out.println(Arrays.toString(plusOne01(new int[]{8, 9, 9, 9})));
        System.out.println(Arrays.toString(plusOne01(new int[]{2, 3, 4, 5, 5, 6, 6})));
        System.out.println(Arrays.toString(plusOne01(new int[]{9, 9, 9})));
    }

    /**
     * 66. 加一
     * 提示：
     * 1 <= digits.length <= 100
     * 0 <= digits[i] <= 9
     */
    public int[] plusOne(int[] digits) {
        // 记录数组末尾数字为9的个数
        int count = 0;
        int length = digits.length;
        for (int i = length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                count++;
            } else {
                break;
            }
        }
        if (count == 0) {
            digits[length - 1] = digits[length - 1] + 1;
            return digits;
        } else {
            for (int i = count; i > 0; i--) {
                digits[length - i] = 0;
            }
            if (length > count) {
                digits[length - count - 1] = digits[length - count - 1] + 1;
                return digits;
            } else {
                int[] newDigits = new int[digits.length + 1];
                newDigits[0] = 1;
                return newDigits;
            }
        }

    }

    /**
     * 看题解后的优化解法
     */
    public int[] plusOne01(int[] digits) {
        int length = digits.length;
        for (int i = length - 1; i >= 0; i--) {
            if (digits[i] == 9) {
                digits[i] = 0;
            } else {
                digits[i] += 1;
                return digits;
            }
        }
        int[] newDigits = new int[length + 1];
        newDigits[0] = 1;
        return newDigits;
    }

    /**
     * 2496.一个由字母和数字组成的字符串的 值 定义如下：
     * <p>
     * 如果字符串 只 包含数字，那么值为该字符串在 10 进制下的所表示的数字。
     * 否则，值为字符串的 长度 。
     * 给你一个字符串数组 strs ，每个字符串都只由字母和数字组成，请你返回 strs 中字符串的 最大值 。
     *
     * @param strs
     * @return
     */
    public int maximumValue(String[] strs) {
        // 记录数组strs中的最大值
        int max = 0;
        for (String str : strs) {
            if (allNum(str)) {
                if (max < Integer.parseInt(str)) {
                    max = Integer.parseInt(str);
                }
            } else {
                if (max < str.length()) {
                    max = str.length();
                }
            }
        }
        return max;
    }

    private boolean allNum(String str) {
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if (aChar < '0' || aChar > '9') {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test_2496() {
        String[] str1 = new String[]{"alic3", "bob", "3", "4", "00000"};
        System.out.println(maximumValue(str1));
        String[] str2 = new String[]{"1", "01", "001", "0001"};
        System.out.println(maximumValue(str2));
        String[] str3 = new String[]{"89", "bob", "3", "4", "00000"};
        System.out.println(maximumValue(str3));
    }

    /**
     * 2032
     */
    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        Map<Integer, Integer> map = new HashMap<>();
        put(nums1, map);
        put(nums2, map);
        put(nums3, map);
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > 1) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    private void put(int[] nums, Map<Integer, Integer> map) {
        Set<Integer> collect = Arrays.stream(nums).boxed().distinct().collect(Collectors.toSet());

        collect.stream().forEach(item -> {
            if (map.containsKey(item)) {
                map.put(item, map.get(item) + 1);
            } else {
                map.put(item, 1);
            }
        });
    }

    @Test
    public void test_2032() {
        System.out.println(twoOutOfThree(new int[]{3, 1}, new int[]{2, 3}, new int[]{1, 2}));
        System.out.println(twoOutOfThree(new int[]{1, 2, 2}, new int[]{4, 3}, new int[]{5}));
    }

    /**
     * 1769. 移动所有球到每个盒子所需的最小操作数
     */
    public int[] minOperations(String boxes) {
        char[] chars = boxes.toCharArray();
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '1') {
                indexList.add(i);
            }
        }
        int[] answer = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            int sum = 0;
            for (Integer index : indexList) {
                sum += Math.abs(index - i);
            }
            answer[i] = sum;
        }
        return answer;
    }

    public void printArray(int[] arr) {
        Arrays.stream(arr).forEach(item -> System.out.print(item + " "));
        System.out.println();
    }

    @Test
    public void test_1769() {
        printArray(minOperations("110"));
        printArray(minOperations("001011"));
    }

    /**
     * 946. 验证栈序列
     *
     * @Author Tracy
     * @Date 2022/11/2
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Deque<Integer> stack = new LinkedList<>();
        int pushIndex = 0;
        int popIndex = 0;
        for (int i = 0; i < pushed.length; i++) {
            if (stack.isEmpty() || stack.peek() != popped[popIndex]) {
                stack.push(pushed[pushIndex++]);
            }
            while (!stack.isEmpty() && stack.peek() == popped[popIndex]) {
                stack.pop();
                popIndex++;
            }
        }
        return pushIndex == popIndex && pushIndex == pushed.length;

    }

    @Test
    public void test_946() {
        int[] pushed = {1, 2, 3, 4, 5};
        int[] popped = {4, 5, 3, 2, 1};
        Assert.assertEquals(true, validateStackSequences(pushed, popped));
    }

    /**
     * 769. 最多能完成排序的块
     * 给定一个长度为 n 的整数数组 arr ，它表示在 [0, n - 1] 范围内的整数的排列。
     * 我们将 arr 分割成若干 块 (即分区)，并对每个块单独排序。将它们连接起来后，使得连接的结果和按升序排序后的原数组相同。
     * 返回数组能分成的最多块数量。
     * <p>
     * 提示:
     * <p>
     * · n == arr.length
     * · 1 <= n <= 10
     * · 0 <= arr[i] < n
     * · arr 中每个元素都 不同
     */
    public int maxChunksToSorted(int[] arr) {
        // 特点：数组arr元素具有特殊性，值唯一
        // 组合排序后各数的大小刚好会等于其下标大小，遍历数组，只要前k个数的最大值为k，则可以计为一个块
        int max = -1;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
            if (i == max) {
                count++;
            }
        }
        return count;
    }

    /**
     * 2706.购买两块巧克力
     */
    public int buyChoco(int[] prices, int money) {
        Arrays.sort(prices);
        return prices[0] + prices[1] <= money ? money - prices[0] - prices[1] : money;
    }

    /**
     * 496.下一个更大元素Ⅰ
     */
    public int[] nextGreaterElementI(int[] nums1, int[] nums2) {
        // 定义哈希表，保存nums2的值与下标，反向遍历nums2，保存单调栈
        HashMap<Integer, Integer> numIndexMap = new HashMap<>();
        int[] nextBigger = new int[nums2.length];
        int[] ans = new int[nums1.length];
        Deque<Integer> deque = new ArrayDeque<>();  // 单调栈
        for (int i = nums2.length - 1; i >= 0; i--) {
            numIndexMap.put(nums2[i], i);
            // 固定结构：栈不为空，若栈顶元素小于候选元素，则出栈直到栈顶元素大于候选
            while (!deque.isEmpty() && deque.peek() < nums2[i]) {
                deque.pop();
            }
            nextBigger[i] = deque.isEmpty() ? -1 : deque.peek();
            // 较大候选元素压栈
            deque.push(nums2[i]);
        }
        for (int i = 0; i < nums1.length; i++) {
            ans[i] = nextBigger[numIndexMap.get(nums1[i])];
        }
        return ans;
    }

    @Test
    public void test_496() {
        // 输入：nums1 = [4,1,2], nums2 = [1,3,4,2].
        // 输出：[-1,3,-1]
        int[] actualResult = nextGreaterElementI(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2});
        System.out.println("输入：nums1 = [4,1,2], nums2 = [1,3,4,2],输出：[-1,3,-1]");
        int[] actualResult1 = nextGreaterElementI(new int[]{2, 4}, new int[]{1, 2, 3, 4});
        System.out.println("输入：nums1 = [2,4], nums2 = [1,2,3,4],输出：[3,-1]");
    }

    /**
     * 503.下一个更大元素Ⅱ
     * <p>
     * nums是一个循环数组
     */
    public int[] nextGreaterElementsII(int[] nums) {
        int[] ans = new int[nums.length];
        // 找到循环数组的最大值，从最大值处开始倒叙循环
        int maxInArray = Integer.MIN_VALUE;
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (maxInArray < nums[i]) {
                maxInArray = nums[i];
                index = i;
            }
        }
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) { // 方向遍历
            // 转换遍历时的index
            int trueIndex = (index + nums.length - i) % nums.length;
            while (!deque.isEmpty() && deque.peek() <= nums[trueIndex]) {
                deque.pop();
            }
            ans[trueIndex] = deque.isEmpty() ? -1 : deque.peek();
            deque.push(nums[trueIndex]);
        }
        return ans;
    }

    @Test
    public void test_503() {
        // 输入: nums = [1,2,3,4,3]
        //输出: [2,3,4,-1,4]
        int[] actualResult = nextGreaterElementsII(new int[]{1, 2, 3, 4, 3});
        System.out.println("输入：nums = [1,2,3,4,3],输出：[2,3,4,-1,4]");
    }

    /**
     * 2333.最小差值平方差
     * <p>
     * 给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，长度为 n 。
     * 数组 nums1 和 nums2 的 差值平方和 定义为所有满足 0 <= i < n 的 (nums1[i] - nums2[i])^2 之和。
     * 同时给你两个正整数 k1 和 k2 。你可以将 nums1 中的任意元素 +1 或者 -1 至多 k1 次。类似的，你可以将 nums2 中的任意元素 +1 或者 -1 至多 k2 次。
     * 请你返回修改数组 nums1 至多 k1 次且修改数组 nums2 至多 k2 次后的最小 差值平方和 。
     * <p>
     * 注意：你可以将数组中的元素变成 负 整数。
     */
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        // 获取num1和num2差值数组dis
        Integer[] dis = new Integer[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            dis[i] = Math.abs(nums1[i] - nums2[i]);
        }
        Arrays.sort(dis, Comparator.reverseOrder());
        // 排序数组dis，贪心减去k1,k2
        int sum = k1 + k2;
        // 时间复杂度过大   TODO 待修改；
        while (sum > 0 && dis[0] > 0) {
            dis[0] -= 1;
            sum--;
            Arrays.sort(dis, Comparator.reverseOrder());
        }
        long res = 0;
        for (int i = 0; i < dis.length; i++) {
            res += dis[i] * dis[i];
        }
        return res;
    }

    @Test
    public void test_2333() {
        // 示例 1：
        //
        //输入：nums1 = [1,2,3,4], nums2 = [2,10,20,19], k1 = 0, k2 = 0
        //输出：579
        //解释：nums1 和 nums2 中的元素不能修改，因为 k1 = 0 和 k2 = 0 。
        //差值平方和为：(1 - 2)2 + (2 - 10)2 + (3 - 20)2 + (4 - 19)2 = 579 。
        //
        Assert.assertEquals(579, minSumSquareDiff(new int[]{1, 2, 3, 4}, new int[]{2, 10, 20, 19}, 0, 0));

        //示例 2：
        //输入：nums1 = [1,4,10,12], nums2 = [5,8,6,9], k1 = 1, k2 = 1
        //输出：43
        //解释：一种得到最小差值平方和的方式为：
        //- 将 nums1[0] 增加一次。
        //- 将 nums2[2] 增加一次。
        //最小差值平方和为：
        //(2 - 5)2 + (4 - 8)2 + (10 - 7)2 + (12 - 9)2 = 43 。
        //注意，也有其他方式可以得到最小差值平方和，但没有得到比 43 更小答案的方案。
        Assert.assertEquals(43, minSumSquareDiff(new int[]{1, 4, 10, 12}, new int[]{5, 8, 6, 9}, 1, 1));

    }

    /**
     * 2397.被列覆盖的最多行数
     */
    public int maximumRows(int[][] matrix, int numSelect) {
        // 可转化为二级制，或运算，等于覆盖数组
        int row = matrix.length;  // 行
        int col = matrix[0].length;
        if (numSelect == col) {
            return row;
        }
        // 遍历区间 2^col - 1
        int size = (int) (Math.pow(2, col));
        int maximumRow = 0;
        for (int i = 0; i < size; i++) {
            // ** 重点 **
            if (numSelect != Integer.bitCount(i)) {
                continue;
            }
            int res = 0;
            // 行当做二进制，转为十进制rowCount，如果rowCount | i = i，则覆盖，否则不能覆盖
            for (int rowNum = 0; rowNum < row; rowNum++) {
                int rowCount = 0;
                for (int num : matrix[rowNum]) {
                    rowCount = (rowCount << 1) + num;
                }
                if ((rowCount | i) == i) {
                    res++;
                }
            }
            maximumRow = Math.max(maximumRow, res); // 当numSelect构成的值为i时，取res的最大值
        }

        return maximumRow;
    }

    @Test
    public void test_2397() {
        // 输入：matrix = [[0,0,0],[1,0,1],[0,1,1],[0,0,1]], numSelect = 2
        // 输出：3
        Assert.assertEquals(3, maximumRows(new int[][]{{0, 0, 0}, {1, 0, 1}, {0, 1, 1}, {0, 0, 1}}, 2));
        // 输入：matrix = [[1],[0]], numSelect = 1
        // 输出：2
        Assert.assertEquals(2, maximumRows(new int[][]{{1}, {0}}, 1));
        // 输入：[[0,1],[1,0]], numSelect = 2
        // 输出：2
        Assert.assertEquals(2, maximumRows(new int[][]{{1, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 1, 1, 1, 1}, {0, 0, 0, 1, 0, 0, 1}}, 5));
    }

    /**
     * 447. 回旋镖的数量
     * <p>
     * 提示：
     * <p>
     * * n == points.length
     * * 1 <= n <= 500
     * * points[i].length == 2
     * * -104 <= xi, yi <= 104
     * * 所有点都 互不相同
     */
    public int numberOfBoomerangs(int[][] points) {
        if (points.length == 1) {
            return 0;
        }
        int ans = 0;
        // 遍历坐标点数组，暴力匹配每一对与其余各个节点距离平方和，记录和出现的次数，由于有序即次数n大于等于2时全排列即（n * (n - 1)）
        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int[] point1 : points) {
            hashMap.clear();
            for (int[] point2 : points) {
                Integer distanse = (point1[0] - point2[0]) * (point1[0] - point2[0]) + (point1[1] - point2[1]) * (point1[1] - point2[1]);
                hashMap.putIfAbsent(distanse, 0);
                hashMap.put(distanse, hashMap.get(distanse) + 1);
            }
            for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
                Integer value = entry.getValue();
                if (value > 1) {
                    ans += value * (value - 1);
                }
            }
        }
        return ans;
    }

    @Test
    public void test_447() {
        // 输入：points = [[0,0],[1,0],[2,0]]
        //输出：2
        //解释：两个回旋镖为 [[1,0],[0,0],[2,0]] 和 [[1,0],[2,0],[0,0]]
        Assert.assertEquals(2, numberOfBoomerangs(new int[][]{{0, 0}, {1, 0}, {2, 0}}));

        // 输入：points = [[1,1],[2,2],[3,3]]
        //输出：2

        //输入：points = [[1,1]]
        //输出：0

    }

    /**
     * 2645.构造有效字符串的最少插入数
     * <p>
     * 给你一个字符串 word ，你可以向其中任何位置插入 "a"、"b" 或 "c" 任意次，返回使 word 有效 需要插入的最少字母数。
     * 如果字符串可以由 "abc" 串联多次得到，则认为该字符串 有效 。
     * <p>
     * 提示：
     * * 1 <= word.length <= 50
     * * word 仅由字母 "a"、"b" 和 "c" 组成。
     */
    public int addMinimum(String word) {
        int cnt = 0;
        word = word.replace("abc", "-");
        while (word.contains("ab")) {
            word = word.replaceFirst("ab", "-");
            cnt++;
        }
        while (word.contains("bc")) {
            word = word.replaceFirst("bc", "-");
            cnt++;
        }
        while (word.contains("ac")) {
            word = word.replaceFirst("ac", "-");
            cnt++;
        }
        word = word.replace("-", "");

        return cnt + word.length() * 2;
    }

    @Test
    public void test_2645() {
        Assert.assertEquals(6, addMinimum("aaabab"));
    }

    public int countWords(String[] words1, String[] words2) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words1) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        Set<String> set = new HashSet<>();
        for (String word : words2) {
            if (set.contains(word)) {
                // 将map中的标记值+1，移除set
//                map.put(word, map.getOrDefault(word, 0) + 1);
                map.merge(word, 1, Integer::sum);
                set.remove(word);
            }
            if (map.getOrDefault(word, 0) == 1) {
                set.add(word);
            }
        }
        return set.size();
    }

    /**
     * 26.删除有序数组中的重复项
     * 快慢指针
     */
    public int removeDuplicates(int[] nums) {
        int point = Integer.MIN_VALUE;
        int cnt = 0;
        int index = 0;
        int length = nums.length;
        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i];
            if (cur > point) {
                point = cur;
                nums[index++] = point;
            } else {
                cnt++;
            }
        }
        return length - cnt;
    }

    /**
     * 27.移除元素
     */
    public int removeElement(int[] nums, int val) {
        int index = 0;  // 慢指针
        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i];
            if (cur != val) {
                nums[index++] = cur;
            }
        }
        return index;
    }

    /**
     * 28.找出字符串中第一个匹配项的下标
     */
    public int strStr(String haystack, String needle) {
        int p1 = 0;
        while (p1 < haystack.length()) {
            int p2 = 0;
            int tempP1 = p1;
            while (tempP1 < haystack.length() && p2 < needle.length() && haystack.charAt(tempP1) == needle.charAt(p2)) {
                p2++;
                tempP1++;
            }
            if (p2 == needle.length()) {
                return tempP1 - p2;
            }
            p1++;
        }
        return -1;
    }

    /**
     * 2182.构造限制重复的字符串
     * <p>
     * 给你一个字符串 s 和一个整数 repeatLimit ，用 s 中的字符构造一个新字符串 repeatLimitedString ，使任何字母 连续 出现的次数都不超过
     * repeatLimit 次。你不必使用 s 中的全部字符。
     * <p>
     * 返回 字典序最大的 repeatLimitedString 。
     * <p>
     * 如果在字符串 a 和 b 不同的第一个位置，字符串 a 中的字母在字母表中出现时间比字符串 b 对应的字母晚，则认为字符串 a 比字符串 b 字典序更大 。
     * 如果字符串中前 min(a.length, b.length) 个字符都相同，那么较长的字符串字典序更大。
     */
    public String repeatLimitedString(String s, int repeatLimit) {
        int[] cnt = new int[26];
        for (char c : s.toCharArray()) {
            cnt[c - 'a'] += 1;
        }
        /**
         * 双指针法：
         * 1）数据初始化，统计出原数组每个字符出现的次数；
         * 2）定义2个指针，最大字符编号 max，次最大字符编号 sec；
         * 3）循环
         *   3.1）如果当前max个数小于等于repeatLimit，则使用完最大字符后，更新最大字符max、次最大字符sec；
         *   3.2）否则，使用完repeatLimit个最大字符后，使用一个次最大字符，更新次最大字符sec（次最大可能无？）；
         *   3.3）最大字符不存在，即可返回
         */
        // 找到出现的字符中字典序最大字母索引
        int maxIndex = 25;
        while (cnt[maxIndex] == 0) {
            maxIndex--;
        }
        // 找到出现的字符中字典序次 最大字母索引
        int secIndex = maxIndex - 1;
        while (secIndex >= 0 && cnt[secIndex] == 0) {
            secIndex--;
        }
        // 遍历组装字符串
        StringBuilder ans = new StringBuilder();
        while (maxIndex >= 0) {
            // 拼接最大序列字符
            int times = Math.min(repeatLimit, cnt[maxIndex]);
            cnt[maxIndex] -= times;
            while (times > 0) {
                ans.append((char) ('a' + maxIndex));
                times--;
            }
            // 次最大序列下标小于0停止
            if (secIndex < 0) {
                break;
            }
            // 最大字符使用完，更新最大、次最大字符，否则拼接次最大，更新次最大下标
            if (cnt[maxIndex] == 0) {
                maxIndex = secIndex;
                secIndex = maxIndex - 1;
            } else {
                ans.append((char) ('a' + secIndex));
                cnt[secIndex]--;
            }
            while (secIndex >= 0 && cnt[secIndex] == 0) {
                secIndex--;
            }
        }
        return ans.toString();
    }

    public String repeatLimitedString2(String s, int repeatLimit) {
        // 统计每个字符出现的次数，即初始可用次数
        int[] counts = new int[26];
        int n = s.length();
        for (int i = 0; i < n; i++) {
            counts[s.charAt(i) - 'a']++;
        }
        // 找到出现的字符中字典序最大字母的索引
        int maxChar = 25;
        while (counts[maxChar] == 0) maxChar--;
        // 找到出现的字符中字典序次大字母的索引
        int secondMaxChar = maxChar - 1;
        while (secondMaxChar >= 0 && counts[secondMaxChar] == 0) secondMaxChar--;
        // 开始构造字符串
        StringBuilder newStr = new StringBuilder();
        while (maxChar >= 0) {
            int repeat = Math.min(repeatLimit, counts[maxChar]);     // 最大字符重复次数取其当前可用次数和最大限制的较小值
            counts[maxChar] -= repeat;      // 更新字符可用次数
            while (repeat-- > 0) newStr.append((char) ('a' + maxChar)); // 重复最大字符
            if (secondMaxChar < 0) break;    // 如果没有可用的次大字符，构造结束
            if (counts[maxChar] == 0) {
                // 最大字符使用完了，使用次大字符
                maxChar = secondMaxChar;
                secondMaxChar--;    // 次大字符索引前移一位，以找到出现字符中下一个次大字符
            } else {
                // 最大字符没用完，肯定用了最大限制个，插入一个次大字符间隔
                newStr.append((char) ('a' + secondMaxChar));
                counts[secondMaxChar]--;    // 更新次大字符可用次数
            }
            while (secondMaxChar >= 0 && counts[secondMaxChar] == 0) secondMaxChar--;    // 确保指针始终指向一个可用的次大字符
        }
        return newStr.toString();
    }

    @Test
    public void test_2182() {
//        Assert.assertEquals("eeddcbaa", repeatLimitedString("abcdeeda", 2));
//        Assert.assertEquals(repeatLimitedString2("pdprlxqryxdirdr", 10), repeatLimitedString("pdprlxqryxdirdr", 10));
        Assert.assertEquals(repeatLimitedString2("bplpcfifosybmjxphbxdltxtfrjspgixoxzbpwrtkopepjxfooazjyosengdlvyfchqhqxznnhuuxhtbrojyhxwlsrklsryvmufoibgfyxgjw", 1),
                repeatLimitedString("bplpcfifosybmjxphbxdltxtfrjspgixoxzbpwrtkopepjxfooazjyosengdlvyfchqhqxznnhuuxhtbrojyhxwlsrklsryvmufoibgfyxgjw", 1));
    }

    /**
     * 2744.最大字符串匹配对数目
     */
    public int maximumNumberOfStringPairs(String[] words) {
        // 哈希
        Set<String> wordSet = Arrays.stream(words).collect(Collectors.toSet());
        int cnt = 0;
        for (String word : words) {
            StringBuilder sb = new StringBuilder();
            char[] chars = word.toCharArray();
            for (int i = chars.length - 1; i >= 0; i--) {
                sb.append(chars[i]);
            }
            wordSet.remove(word); // 先移除自身
            if (wordSet.contains(sb.toString())) {
                cnt++;
                wordSet.remove(sb.toString());
            }
        }
        return cnt;
    }

    @Test
    public void test_2744() {
        Assert.assertEquals(2, maximumNumberOfStringPairs(new String[]{"cd", "ac", "dc", "ca", "zz"}));
    }

    /**
     * 2171.拿出最少数目的魔法豆
     */
    public long minimumRemoval(int[] beans) {
        long min = Long.MAX_VALUE;
        Arrays.sort(beans); // 排列
        // 遍历，每一个数为最终相等的个数时，大于beans[i]的，拿出beans[x]-beans[i]个，小于beans[i]的全部拿出
        long sum = Arrays.stream(beans).mapToLong(item -> item).sum();
        for (int i = 0; i < beans.length; i++) {
            if (i - 1 > 0 && beans[i] == beans[i - 1]) continue;
            min = Math.min(min, sum - (long) beans[i] * (beans.length - i));
        }
        return min;
    }

    @Test
    public void test_2171() {
//        输入：beans = [2,10,3,2]
//        输出：7
        Assert.assertEquals(7, minimumRemoval(new int[]{2, 10, 3, 2}));
    }

    /**
     * 29.两数相除
     * <p>
     * 给你两个整数，被除数 dividend 和除数 divisor。将两数相除，要求 不使用 乘法、除法和取余运算。
     * <p>
     * 整数除法应该向零截断，也就是截去（truncate）其小数部分。例如，8.345 将被截断为 8 ，-2.7335 将被截断至 -2 。
     * <p>
     * 返回被除数 dividend 除以除数 divisor 得到的 商 。
     * <p>
     * 注意：假设我们的环境只能存储 32 位 有符号整数，其数值范围是 [−2^31,  2^31 − 1] 。本题中，如果商 严格大于 2^31 − 1 ，则返回 2^31 − 1 ；如果商 严格小于 -2^31 ，则返回 -2^31 。
     */
    public int divide(int dividend, int divisor) {
        // 处理边界
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;
        if (divisor == 0) return 0;
        if (divisor == Integer.MIN_VALUE) return dividend == Integer.MIN_VALUE ? 1 : 0;

        // 全部转换为正数，由于dividend可能为Min_Value(2^32 - 1)，转正数超出范围，故使用long来记性转换
        int flag = 1;
        long a = dividend;
        long b = divisor;
        if (a < 0) {
            a = -a;
            flag *= -1;
        }
        if (b < 0) {
            b = -b;
            flag *= -1;
        }
        long ans = div(a, b);
        return (int) (flag > 0 ? ans : -ans);
    }

    /**
     * 倍增法实现乘法
     */
    public long multy(long a, long b) {
        long ans = 0;
        while (b > 0) {
            if ((b & 1) == 1) ans += a;
            b = b >> 1;
            a += a;
        }
        return ans;
    }

    /**
     * 倍减法实现除法
     */
    public long div(long a, long b) {
        if (a < b) return 0;
        int cnt = 1;  // a 大于 b时，商至少为1，开始加倍减
        long temp = b; // 暂存除数
        while (a > (temp << 1)) { // a > b * 2 ，a 比 b的2倍大，则商至少为2
            cnt = cnt << 1;   // cnt * 2
            temp = temp << 1;  // temp * 2
        }
        return cnt + div(a - temp, b);
    }

    /**
     * 670.最大交换
     * 给定一个非负整数，你至多可以交换一次数字中的任意两位。返回你能得到的最大值。
     */
    public int maximumSwap(int num) {
        char[] chars = String.valueOf(num).toCharArray();
        // 双指针，first,max，找到后面一个下标max比first大的，交换
        for (int i = 0; i < chars.length; i++) {
            int max = i;
            for (int j = i; j < chars.length; j++) {
                if (chars[j] > chars[i] && chars[j] >= chars[max]) {
                    max = j;
                }
            }
            if (max > i) {
                char temp = chars[max];
                chars[max] = chars[i];
                chars[i] = temp;
                break;
            }
        }
        return Integer.parseInt(String.valueOf(chars));
    }

    @Test
    public void test_670() {
        Assert.assertEquals(98863, maximumSwap(98368));
        Assert.assertEquals(9913, maximumSwap(1993));
    }

    /**
     * 2765.最长交替子数组
     * 给你一个下标从 0 开始的整数数组 nums 。如果 nums 中长度为 m 的子数组 s 满足以下条件，我们称它是一个 交替子数组 ：
     * <p>
     * m 大于 1 。
     * s1 = s0 + 1 。
     * 下标从 0 开始的子数组 s 与数组 [s0, s1, s0, s1,...,s(m-1) % 2] 一样。也就是说，s1 - s0 = 1 ，s2 - s1 = -1 ，s3 - s2 = 1 ，
     * s4 - s3 = -1 ，以此类推，直到 s[m - 1] - s[m - 2] = (-1)m 。
     */
    public int alternatingSubarray(int[] nums) {
        int max = -1;
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            int move = i + 1;
            int change = 1;
            while (move < length) {
                if (nums[move] - nums[move - 1] == change) {
                    change *= -1;
                    move++;
                } else {
                    break;
                }
            }
            if (move == i + 1) continue;
            max = Math.max(max, move - i);
        }
        return max;
    }

    @Test
    public void test_2765() {
        Assert.assertEquals(4, alternatingSubarray(new int[]{2, 3, 4, 3, 4}));
        Assert.assertEquals(2, alternatingSubarray(new int[]{2, 3}));
    }


    public int search(int[] nums, int target) {
        // 依题意，数组：0 - k - end ，存在已个下标k，0 - k 递增，k- end 递增，且 nums[k] > nums[k + 1]
        // 从end -- 0 遍历，找到nums[k] > nums[k + 1]的k值

        int length = nums.length;
        if (length == 0) return -1;
        if (length == 1) return nums[0] == target ? 0 : -1;
        int k = 0;
        for (int i = length - 1; i > 0; i--) {
            if (nums[i] < nums[i - 1]) {
                k = i;
                break;
            }
        }
        // 原数组分为2段，0 -- (k - 1) 和 k -- end，前后2段均为升序，且nums[0] > nums[end]，如果target<nums[0]，则遍历前半段，否则遍历后半段
        int left, right;
        if (target >= nums[0]) {
            left = 0;
            right = k - 1;
        } else {
            left = k;
            right = length - 1;
        }
        int find = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                find = mid;
                break;
            }
        }
        return find;
    }

    @Test
    public void test_33() {
//        示例 1：
//
//        输入：nums = [4,5,6,7,0,1,2], target = 0
//        输出：4
//        Assert.assertEquals(4, search(new int[]{4,5,6,7,0,1,2}, 0));
//
//        示例 2：
//        输入：nums = [4,5,6,7,0,1,2], target = 3
//        输出：-1
//        Assert.assertEquals(-1, search(new int[]{4,5,6,7,0,1,2}, 3));
//        示例 3：
//        输入：nums = [1], target = 0
//        输出：-1
//        Assert.assertEquals(-1, search(new int[]{1}, 0));
        Assert.assertEquals(0, search(new int[]{1, 3}, 1));
    }

    /**
     * 36.有效的数独
     * 注意：遍历一次，同事获取到行、列、宫格内的元素，难点宫格的下标index和row/col的关系 （index=(row/3) * 3 + col/3）
     */
    public boolean isValidSudoku(char[][] board) {
        // 数字 1-9 在每一行只能出现一次
        for (int i = 0; i < board.length; i++) {
            int numCnt = 0;
            Set<Character> set = new HashSet<>();
            char[] currArr = board[i];
            for (int j = 0; j < currArr.length; j++) {
                if (currArr[j] >= '1' && currArr[j] <= '9') {
                    set.add(currArr[j]);
                    numCnt++;
                }
            }
            // 存在数值，且数字的个数不等于set的size，即说明有重复的数字，返回false
            if (numCnt != 0 && numCnt != set.size()) {
                return false;
            }
        }
        // 数字 1-9 在每一列只能出现一次。
        for (int col = 0; col < board[0].length; col++) {
            int numCnt = 0;
            Set<Character> set = new HashSet<>();
            for (int row = 0; row < board.length; row++) {
                char curr = board[row][col];
                if (curr >= '1' && curr <= '9') {
                    set.add(curr);
                    numCnt++;
                }
            }
            // 存在数值，且数字的个数不等于set的size，即说明有重复的数字，返回false
            if (numCnt != 0 && numCnt != set.size()) {
                return false;
            }
        }

        // 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                // 枚举出每个宫格内的元素集合
                char[] arr = new char[]{
                        board[row][col], board[row][col + 1], board[row][col + 2],
                        board[row + 1][col], board[row + 1][col + 1], board[row + 1][col + 2],
                        board[row + 2][col], board[row + 2][col + 1], board[row + 2][col + 2]
                };
                int numCnt = 0;
                Set<Character> set = new HashSet<>();
                for (char curr : arr) {
                    if (curr >= '1' && curr <= '9') {
                        set.add(curr);
                        numCnt++;
                    }
                }
                // 存在数值，且数字的个数不等于set的size，即说明有重复的数字，返回false
                if (numCnt != 0 && numCnt != set.size()) {
                    return false;
                }
            }
        }
        return true;

    }


    public boolean isValidSudokuII(char[][] board) {
        // 注意：遍历一次，同事获取到行、列、宫格内的元素，难点宫格的下标index和row/col的关系 （index=(row/3) * 3 + col/3）
        // 定义3个集合，分别保存行、列、宫格的数字
        Map<Integer, List<Integer>> rowMap = new HashMap<>();
        Map<Integer, List<Integer>> colMap = new HashMap<>();
        Map<Integer, List<Integer>> squareMap = new HashMap<>(); // index=(row/3) * 3 + col/3
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                char cur = board[row][col];
                if ('.' == cur) continue;
                int num = cur - '0';
                List<Integer> rowList = rowMap.computeIfAbsent(row, key -> new ArrayList<>());
                List<Integer> colList = colMap.computeIfAbsent(col, key -> new ArrayList<>());
                int squareIndex = row / 3 * 3 + col / 3;
                List<Integer> squareList = squareMap.computeIfAbsent(squareIndex, key -> new ArrayList<>());
                if (rowList.contains(num) || colList.contains(num) || squareList.contains(num)) {
                    return false;
                } else {
                    rowList.add(num);
                    colList.add(num);
                    squareList.add(num);
                }
            }
        }
        return true;
    }

    @Test
    public void test_36() {
        // 输入：board =
        //[["5","3",".",".","7",".",".",".","."]
        //,["6",".",".","1","9","5",".",".","."]
        //,[".","9","8",".",".",".",".","6","."]
        //,["8",".",".",".","6",".",".",".","3"]
        //,["4",".",".","8",".","3",".",".","1"]
        //,["7",".",".",".","2",".",".",".","6"]
        //,[".","6",".",".",".",".","2","8","."]
        //,[".",".",".","4","1","9",".",".","5"]
        //,[".",".",".",".","8",".",".","7","9"]]
        //输出：true
        Assert.assertEquals(true, isValidSudoku(new char[][]{{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}}));
        //示例 2：
        //
        //输入：board =
        //[["8","3",".",".","7",".",".",".","."]
        //,["6",".",".","1","9","5",".",".","."]
        //,[".","9","8",".",".",".",".","6","."]
        //,["8",".",".",".","6",".",".",".","3"]
        //,["4",".",".","8",".","3",".",".","1"]
        //,["7",".",".",".","2",".",".",".","6"]
        //,[".","6",".",".",".",".","2","8","."]
        //,[".",".",".","4","1","9",".",".","5"]
        //,[".",".",".",".","8",".",".","7","9"]]
        //输出：false
        //解释：除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。 但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无效的。
    }


    /**
     * 2670.找出不同元素数目差数组  (可优化)
     */
    public int[] distinctDifferenceArray(int[] nums) {
        int[] ans = new int[nums.length];
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            set1.clear();
            set2.clear();
            for (int m = 0; m <= i; m++) {
                set1.add(nums[m]);
            }
            for (int j = i + 1; j < nums.length; j++) {
                set2.add(nums[j]);
            }
            ans[i] = set1.size() - set2.size();
        }
        return ans;
    }

    // 优化
    public int[] distinctDifferenceArrayII(int[] nums) {
        int[] ans = new int[nums.length];
        // 后缀个数
        int[] sufNum = new int[nums.length + 1];
        Set<Integer> sufSet = new HashSet<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            sufSet.add(nums[i]);
            sufNum[i] = sufSet.size();
        }
        // 前缀个数
        Set<Integer> preSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            preSet.add(nums[i]);
            ans[i] = preSet.size() - sufNum[i + 1];
        }
        return ans;
    }

    /**
     * 1686.石子游戏 VI
     * Alice 和 Bob 轮流玩一个游戏，Alice 先手。
     * <p>
     * 一堆石子里总共有 n 个石子，轮到某个玩家时，他可以 移出 一个石子并得到这个石子的价值。Alice 和 Bob 对石子价值有 不一样的的评判标准 。双方都知道对方的评判标准。
     * <p>
     * 给你两个长度为 n 的整数数组 aliceValues 和 bobValues 。aliceValues[i] 和 bobValues[i] 分别表示 Alice 和 Bob 认为第 i 个石子的价值。
     * <p>
     * 所有石子都被取完后，得分较高的人为胜者。如果两个玩家得分相同，那么为平局。两位玩家都会采用 最优策略 进行游戏。
     * <p>
     * 请你推断游戏的结果，用如下的方式表示：
     * <p>
     * 如果 Alice 赢，返回 1 。
     * 如果 Bob 赢，返回 -1 。
     * 如果游戏平局，返回 0 。
     * <p>
     * 分析：因为n个石子，一人取一个。假设alice数组的价值总值A，bob的为B。假设bob拿走了所有的石子，则bob得到的石子的价值是B，alice的价值是0，则得分差值：0 - B；
     * 由于alice也要拿石子，如果拿的下标为i的石子，则分差值会减少：aliceValues[i] + bobValues[i]，即bob只取i石子，差值为：0 - B - （aliceValues[i] + bobValues[i]），
     * 由此可分析得知，alice取 aliceValues[i] + bobValues[i] 最大的值时，alice可获取最大的价值，又由于二人都按最优策略进行，则可定义C数组表示 aliceValues，bobValues
     * 数组相同石子的价值和，并且进行降序排列，则alice、bob以此从前往后取石子即为最有取法。
     */
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        Integer[] c = new Integer[aliceValues.length];
        for (int i = 0; i < c.length; i++) {
            c[i] = i;
        }
        // 排序巧妙，按aliceValues[i] + bobValues[i]降序排列()
        Arrays.sort(c, (o1, o2) -> aliceValues[o2] + bobValues[o2] - aliceValues[o1] - bobValues[o1]);
        int diff = 0;
        for (int i = 0; i < c.length; i++) {
            Integer index = c[i];
            diff += (i % 2 == 0 ? aliceValues[index] : -bobValues[index]);
        }
        return diff > 0 ? 1 : (diff == 0 ? 0 : -1);

    }

    @Test
    public void test_1686() {
        // 输入：aliceValues = [1,2], bobValues = [3,1]
        //输出：0
        //解释：
        //Alice 拿石子 0 ， Bob 拿石子 1 ，他们得分都为 1 分。
        //打平。
        Assert.assertEquals(1, stoneGameVI(new int[]{1, 3}, new int[]{2, 1}));
    }

    /**
     * 204.计数质数
     * <p>
     * 利用枚举的方法，
     */
    public int countPrimes(int n) {
        return 0;
    }


    /**
     * 2808.使循环数组所有元素相等的最少秒数
     */
    public int minimumSeconds(List<Integer> nums) {
        return 0;
    }


    /**
     * 514.自由之路
     */
    public int findRotateSteps(String ring, String key) {

        return 0;
    }

    @Test
    public void test_514() {
        // 输入: ring = "godding", key = "gd"
        //输出: 4

        //输入: ring = "godding", key = "godding"
        //输出: 13
    }


}

