package com.geek.tracy.leetcode;

import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author Tracy
 * @Date 2022/12/30
 */
public class Code_2032 {

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
    public void test01() {
        System.out.println(twoOutOfThree(new int[]{3,1}, new int[]{2,3}, new int[]{1,2}));
        System.out.println(twoOutOfThree(new int[]{1,2,2}, new int[]{4,3}, new int[]{5}));
    }
}
