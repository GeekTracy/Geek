package com.geek.tracy.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Tracy
 * @Date 2023/10/12
 */
public class UtilTest {


    @Test
    public void test() {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13);
        System.out.println("数组分区大小：5，分区结果为：\r\n" + ListUtil.partition(list, 5));
    }

}
