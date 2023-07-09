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
    public void threeSumTest() {
//        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(threeSum(new int[] {34,55,79,28,46,33,2,48,31,-3,84,71,52,-3,93,15,21,-43,57,-6,86,56,94,74,83,-14,28,-66,46,-49,62,-11,43,65,77,12,47,61,26,1,13,29,55,-82,76,26,15,-29,36,-29,10,-70,69,17,49}));
    }

    /**
     * 15. 三数之和
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        int size = nums.length;

        List<List<Integer>> res = new ArrayList<>();

        // 去重
        List<String> only = new ArrayList<>();
        for (int i = 1; i < 1 << size; i++) {
            if (Integer.bitCount(i) == 3) {
                String binary = Integer.toBinaryString(i);
                char[] chars = binary.toCharArray();
                int length = chars.length;
                int sum = 0;
                List<Integer> temp = new ArrayList<>();
                for (int j = length - 1; j >= 0; j--) {
                    if (chars[j] == '1') {
                        sum += nums[length - 1 - j];
                        temp.add(nums[length - 1 - j]);
                    }
                }
                if (sum == 0 && temp.size() == 3) {
                    Collections.sort(temp);
                    String ts = "" + temp.get(0) + temp.get(1) + temp.get(2);
                    if (!only.contains(ts)) {
                        only.add(ts);
                        res.add(temp);
                    }
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
