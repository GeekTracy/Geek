package com.geek.tracy.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 合集
 * @Author Tracy
 * @Date 2023/6/26
 */
public class CodeSets {

    @Test
    public void mergeTest() {
        int[] nums1 = new int[] {1,2,3,0,0,0} ;
        int[] nums2 = new int[] {2,5,6} ;
        merge(nums1, 3, nums2, 3);

        int[] nums11 = new int[] {1} ;
        int[] nums22 = new int[] {} ;
        merge(nums11, 1, nums22, 0);

        int[] nums111 = new int[] {0} ;
        int[] nums222 = new int[] {1} ;
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
            } else  if (nums1[m - 1] > nums2[n - 1]) {
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
        maxAbsoluteSum(new int[] {1,-3,2,3,-4});
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
     *
     * 输入：fronts = [1,2,4,4,7], backs = [1,3,4,1,3]
     * 输出：2
     * 解释：假设我们翻转第二张卡片，那么在正面的数变成了 [1,3,4,4,7] ， 背面的数变成了 [1,2,4,1,3]。
     * 接着我们选择第二张卡片，因为现在该卡片的背面的数是 2，2 与任意卡片上正面的数都不同，所以 2 就是我们想要的数字。
     *
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
     *
     * -2 ：向左转 90 度
     * -1 ：向右转 90 度
     * 1 <= x <= 9 ：向前移动 x 个单位长度
     * 在网格上有一些格子被视为障碍物 obstacles 。第 i 个障碍物位于网格点  obstacles[i] = (xi, yi) 。
     *
     * 提示：
     *
     * 1 <= commands.length <= 10^4
     * commands[i] is one of the values in the list [-2,-1,1,2,3,4,5,6,7,8,9].
     * 0 <= obstacles.length <= 10^4
     * -3 * 10^4 <= xi, yi <= 3 * 10^4
     * 答案保证小于 2^31
     *
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
                            if (intSet.contains((curr_x - 1)  * 60001 + curr_y)){
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
                            if (intSet.contains(curr_x  * 60001 + (curr_y - 1))) {
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
                            if (intSet.contains((curr_x + 1)  * 60001 + curr_y)) {
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
     *
     * 给你一个正整数 n 。n 中的每一位数字都会按下述规则分配一个符号：
     *
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
            num ++;
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
        System.out.println(threeSumClosest(new int[]{0,1,2}, 3));
    }

    /**
     * 16. 最接近的三数之和
     * 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
     * 返回这三个数的和。假定每组输入只存在恰好一个解。
     *
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
    public void test01 () {
        twoSum(new int[]{0,0,3,4}, 0);
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
     *
     * 给你一个下标从 0 开始的二维整数数组 nums 。一开始你的分数为 0 。你需要执行以下操作直到矩阵变为空：
     *
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
        char [] char1 = word1.toCharArray() ;
        char [] char2 = word2.toCharArray() ;
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
     *      1 <= n <= 1000
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

}
