package com.geek.tracy.leetcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Author Tracy
 * @Date 2022/10/13
 */
public class Code_769 {

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
     * 示例 1:
     * <p>
     * 输入: arr = [4,3,2,1,0]
     * 输出: 1
     * 解释:
     * 将数组分成2块或者更多块，都无法得到所需的结果。
     * 例如，分成 [4, 3], [2, 1, 0] 的结果是 [3, 4, 0, 1, 2]，这不是有序的数组。
     */
    @Test
    public void test01() {
        Assert.assertEquals(1, maxChunksToSorted(new int[]{4, 3, 2, 1, 0}));
    }

    /**
     * 示例 2:
     *
     * 输入: arr = [1,0,2,3,4]
     * 输出: 4
     * 解释:
     * 我们可以把它分成两块，例如 [1, 0], [2, 3, 4]。
     * 然而，分成 [1, 0], [2], [3], [4] 可以得到最多的块数。
     */
    @Test
    public void test02() {
        Assert.assertEquals(4, maxChunksToSorted(new int[]{1,0,2,3,4}));
    }
    @Test
    public void test03() {
        Assert.assertEquals(2, maxChunksToSorted(new int[]{1,2,0,3}));
    }
}

