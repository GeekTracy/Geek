package com.geek.tracy.leetcode;

import com.geek.tracy.leetcode.tree.bean.ListNode;
import org.junit.Test;

import java.util.*;

/**
 * 合集
 * @Author Tracy
 * @Date 2023/6/26
 */
public class CodeSets {


    @Test
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
    public void threeSumTest() {
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(threeSum(new int[]{0, 0, 0}));
        System.out.println(threeSum(new int[]{0, 0, 0, 0}));
    }

    /**
     * 15. 三数之和
     *      排序 + 双指针法：将数组排序，取下标i的之后的2个指针：l、r，且i < l < r，遍历数组
     *  提示：
     *  *   3 <= nums.length <= 3000
     *  *   -10^5 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return res;
        }
        // 排序
        Arrays.sort(nums);
        // 遍历数组的元素，由于求的是和为0，又数组已排序
        for (int i = 0; i < nums.length; i++) {
            // 如果下标i的元素大于0，则3数之和不可能为0，终止循环
            if (nums[i] > 0) {
                break;
            }
            // 本题存在重复元素，当nums[i] = nums[i - 1]，则遍历i时会出现重复结果，需要跳过
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 双指针，l、r一前一后，收缩遍历
            int l = i + 1;
            int r = nums.length - 1;

            while (l < r) {
                int sum = nums[i] + nums[l] + nums[r];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    // 左指针，重复值，取最右
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    // 右指针，重复值，取最左
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    l++;
                    r--;
                } else if (sum < 0) {
                    l++;
                } else {
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
     * 445. 两数相加 II
     * 高位在链表表头，使用栈保存，FILO先进后出
     */
    public ListNode addTwoNumbersII(ListNode l1, ListNode l2) {
        Deque<ListNode> stack1 = new LinkedList<>();
        Deque<ListNode> stack2 = new LinkedList<>();

        while (l1 != null) {
            stack1.offerLast(l1);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.offerLast(l2);
            l2 = l2.next;
        }
        Deque<ListNode> result = new LinkedList<>();
        boolean more = false;
        // 遍历stack1 和 stack2,
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            int sum = 0;
            if (!stack1.isEmpty()) {
                sum += stack1.pollLast().val;
            }
            if (!stack2.isEmpty()) {
                sum += stack2.pollLast().val;
            }
            // 是否进位
            if (more) {
                sum++;
                more = false;
            }
            if (sum > 9) {
                more = true;
                sum -= 10;
            }
            result.offerLast(new ListNode(sum));
        }
        if (more) {
            result.offerLast(new ListNode(1));
        }
        // 栈result转为链表，表头为root
        ListNode root = result.pollLast();
        ListNode temp = root;
        while (!result.isEmpty()) {
            temp.next = result.peekLast();
            temp = result.pollLast();
        }
        return root;
    }

    @Test
    public void addTwoNumbersTest() {
//        l1 = [2,4,3]
//        l2 = [5,6,4]
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        addTwoNumbers(l1, l2);
    }

    /**
     * 2. 两数相加
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode tempRoot = new ListNode();
        ListNode tempL1 = l1;
        ListNode tempL2 = l2;
        ListNode resultTemp = tempRoot;
        boolean more = false;
        while (tempL1 != null || tempL2 != null || more) {
            int sum = 0;
            if (tempL1 != null) {
                sum += tempL1.val;
                tempL1 = tempL1.next;
            }
            if (tempL2 != null) {
                sum += tempL2.val;
                tempL2 = tempL2.next;
            }
            if (more) {
                sum++;
            }
            ListNode curr;
            if (sum > 9) {
                curr = new ListNode(sum - 10);
                more = true;
            } else {
                curr = new ListNode(sum);
                more = false;
            }
            resultTemp.next = curr;
            resultTemp = curr;
        }
        return tempRoot.next;
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
