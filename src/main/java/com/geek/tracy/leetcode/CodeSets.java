package com.geek.tracy.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 合集
 *
 * @Author Tracy
 * @Date 2023/6/26
 */
public class CodeSets {


    @Test
    public void test_2865 () {
//        Assert.assertEquals(13, maximumSumOfHeightsI(Arrays.asList(5,3,4,1,1)));
        Assert.assertEquals(22, maximumSumOfHeightsI(Arrays.asList(6,5,3,9,2,7)));
        Assert.assertEquals(18, maximumSumOfHeightsI(Arrays.asList(3,2,5,5,2,3)));
    }
    /**
     * 2865.美丽塔 Ⅰ
     * 存在一个峰顶下标i，其左右两边分别递增、递减
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
    public void test_2866 () {
        List<Integer> list = new ArrayList<>();

    }

    /**
     * 2866.美丽塔 Ⅱ
     *  范围增大，暴力解法超时，需要使用单调栈
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
        movesToMakeZigzag(new int[]{7,4,8,9,7,7,5});
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
     *
     * 题解说明：存在两种锯齿，偶数下标或奇数下标为齿峰，不管哪一种，齿峰值不变。
     *
     * * 以偶下标为齿峰为例：则nums[0]、nums[2]、nums[4]...不变，改变奇数下标nums[1]、nums[3]、nums[5]使其成为齿谷。
     * * 若下标i为要改变为峰谷的数，则需要改动的次数为：nums[i - 1]和nums[i + 1]中较小的为：min= min(nums[i - 1], nums[i + 1])，
     * * 要移动的次数为：count = num[i] - min + 1 次，则又可优化为：count = max(num[i] - min + 1, 0)次.(说明：小于0不需要修改，即count=0)
     *
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
     *
     * 如果字符串 只 包含数字，那么值为该字符串在 10 进制下的所表示的数字。
     * 否则，值为字符串的 长度 。
     * 给你一个字符串数组 strs ，每个字符串都只由字母和数字组成，请你返回 strs 中字符串的 最大值 。
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
    public void test_2496 () {
        String[] str1 = new String[] {"alic3","bob","3","4","00000"};
        System.out.println(maximumValue(str1));
        String[] str2 = new String[] {"1","01","001","0001"};
        System.out.println(maximumValue(str2));
        String[] str3 = new String[] {"89","bob","3","4","00000"};
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
        System.out.println(twoOutOfThree(new int[]{3,1}, new int[]{2,3}, new int[]{1,2}));
        System.out.println(twoOutOfThree(new int[]{1,2,2}, new int[]{4,3}, new int[]{5}));
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
        int [] answer = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            int sum = 0;
            for (Integer index : indexList) {
                sum += Math.abs(index - i);
            }
            answer[i] = sum;
        }
        return answer;
    }

    public void printArray(int [] arr) {
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
    public void test_946 () {
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
        return prices[0] + prices[1] <= money ? money - prices[0] - prices[1]  : money;
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
        for (int i = nums2.length - 1; i >= 0 ; i--) {
            numIndexMap.put(nums2[i], i);
            // 固定结构：栈不为空，若栈顶元素小于候选元素，则出栈知道栈顶元素大于候选
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
        int[] actualResult1 = nextGreaterElementI(new int[]{2,4}, new int[]{1,2,3,4});
        System.out.println("输入：nums1 = [2,4], nums2 = [1,2,3,4],输出：[3,-1]");
    }

    /**
     * 503.下一个更大元素Ⅱ
     *
     * nums是一个循环数组
     */
    public int[] nextGreaterElementsII(int[] nums) {
        int[] ans = new int [nums.length];
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
        int[] actualResult = nextGreaterElementsII(new int[]{1,2,3,4,3});
        System.out.println("输入：nums = [1,2,3,4,3],输出：[2,3,4,-1,4]");
    }

    /**
     * 2333.最小差值平方差
     *
     * 给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，长度为 n 。
     * 数组 nums1 和 nums2 的 差值平方和 定义为所有满足 0 <= i < n 的 (nums1[i] - nums2[i])^2 之和。
     * 同时给你两个正整数 k1 和 k2 。你可以将 nums1 中的任意元素 +1 或者 -1 至多 k1 次。类似的，你可以将 nums2 中的任意元素 +1 或者 -1 至多 k2 次。
     * 请你返回修改数组 nums1 至多 k1 次且修改数组 nums2 至多 k2 次后的最小 差值平方和 。
     *
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
        Assert.assertEquals(579, minSumSquareDiff(new int[]{1,2,3,4}, new int[]{2,10,20,19}, 0, 0));

        //示例 2：
        //输入：nums1 = [1,4,10,12], nums2 = [5,8,6,9], k1 = 1, k2 = 1
        //输出：43
        //解释：一种得到最小差值平方和的方式为：
        //- 将 nums1[0] 增加一次。
        //- 将 nums2[2] 增加一次。
        //最小差值平方和为：
        //(2 - 5)2 + (4 - 8)2 + (10 - 7)2 + (12 - 9)2 = 43 。
        //注意，也有其他方式可以得到最小差值平方和，但没有得到比 43 更小答案的方案。
        Assert.assertEquals(43, minSumSquareDiff(new int[]{1,4,10,12}, new int[]{5,8,6,9}, 1, 1));

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
        Assert.assertEquals(3, maximumRows(new int[][]{{0,0,0},{1,0,1},{0,1,1},{0,0,1}}, 2));
        // 输入：matrix = [[1],[0]], numSelect = 1
        // 输出：2
        Assert.assertEquals(2, maximumRows(new int[][]{{1},{0}}, 1));
        // 输入：[[0,1],[1,0]], numSelect = 2
        // 输出：2
        Assert.assertEquals(2, maximumRows(new int[][]{{1,0,0,0,0,0,0},{0,1,0,1,1,1,1},{0,0,0,1,0,0,1}}, 5));
    }

    /**
     * 447. 回旋镖的数量
     *
     * 提示：
     *
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
        Assert.assertEquals(2, numberOfBoomerangs(new int[][]{{0,0}, {1,0}, {2,0}}));

        // 输入：points = [[1,1],[2,2],[3,3]]
        //输出：2

        //输入：points = [[1,1]]
        //输出：0

    }



}

