package com.geek.tracy.leetcode;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1262. 可被三整除的最大和
 * 提示：
 * <p>
 * 1 <= nums.length <= 4 * 10^4
 * 1 <= nums[i] <= 10^4
 */
public class Code_1262 {

    public int maxSumDivThree(int[] nums) {
        // 遍历数组，n个数的和、n-1个数的和，n-2个数的和......，依次记录，得出最大的和
        // 优化：先将可以被3整除的取出，其余的进行最大和运算
        // 可以被3整除的，求和
        Integer canDivideSum = 0;
        // 不可以被3整除的
        List<Integer> cannotDivideThree = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 3 == 0) {
                canDivideSum += nums[i];
            } else {
                cannotDivideThree.add(nums[i]);
            }
        }

        Integer sum = 0;
        for (Integer item : cannotDivideThree) {
            sum += item;
        }
        Integer max = 0;
        int index = 0;
//        getMax(cannotDivideThree, sum, index);

        return 0;
    }


    /**
     *
     * @param cannotDivideThree 数组
     * @param sum 当前总和
     * @param tempArray 临时数组，保存去掉的数据的下标
     * @param count 减掉的数据的个数
     */
    private void getMax(List<Integer> cannotDivideThree, Integer sum, List<Integer> tempArray, int max, int count) {
        // 退出条件：
        // 调用下一个
        // 还原值
    }

    public static void main(String[] args) {
        test01();
    }

    /**
     * 用例1
     * 输入：nums = [3,6,5,1,8]
     * 输出：18
     * 解释：选出数字 3, 6, 1 和 8，它们的和是 18（可被 3 整除的最大和）。
     */
    @Test
    public static void test01() {

    }

    /**
     * 用例2
     * 输入：nums = [4]
     * 输出：0
     * 解释：4 不能被 3 整除，所以无法选出数字，返回 0。
     */
    @Test
    public void test02() {

    }

    /**
     * 用例3
     * 输入：nums = [1,2,3,4,4]
     * 输出：12
     * 解释：选出数字 1, 3, 4 以及 4，它们的和是 12（可被 3 整除的最大和）。
     */
    @Test
    public void test03() {

    }

}
