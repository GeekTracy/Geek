package com.geek.tracy.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1401. 圆和矩形是否有重叠
 * @Author Tracy
 * @Date 2023/6/25
 */
public class Code_1401 {

    @Test
    public void test() {
//        输入：radius = 1, xCenter = 0, yCenter = 0, x1 = 1, y1 = -1, x2 = 3, y2 = 1
//        输出：true
        System.out.println(checkOverlap(1, 0 ,0, 1, -1, 3, 1));
//        输入：radius = 1, xCenter = 1, yCenter = 1, x1 = 1, y1 = -3, x2 = 2, y2 = -1
//        输出：false
        System.out.println(checkOverlap(1, 1 ,1, 1, -3, 2, -1));
//        输入：radius = 1, xCenter = 0, yCenter = 0, x1 = -1, y1 = 0, x2 = 0, y2 = 1
//        输出：true
        System.out.println(checkOverlap(1, 0 ,0, -1, 0, 0, 1));
//        1
//        1
//        1
//                -3
//                -3
//        3
//        3
        System.out.println(checkOverlap(1, 1 ,1, -3, -3, 3, 3));


    }

    @Test
    public void test02() {
        System.out.println(checkOverlap(1, 0 ,0, 1, -1, 3, 1));
    }

    /**
     * 思路：区域分析法
     */
    public boolean checkOverlap(int radius, int xCenter, int yCenter, int x1, int y1, int x2, int y2) {
        /* 圆心在矩形内部 */
        if (x1 <= xCenter && xCenter <= x2 && y1 <= yCenter && yCenter <= y2) {
            return true;
        }
        /* 圆心在矩形上部 */
        if (x1 <= xCenter && xCenter <= x2 && y2 <= yCenter && yCenter <= y2 + radius) {
            return true;
        }
        /* 圆心在矩形下部 */
        if (x1 <= xCenter && xCenter <= x2 && y1 - radius <= yCenter && yCenter <= y1) {
            return true;
        }
        /* 圆心在矩形左部 */
        if (x1 - radius <= xCenter && xCenter <= x1 && y1 <= yCenter && yCenter <= y2) {
            return true;
        }
        /* 圆心在矩形右部 */
        if (x2 <= xCenter && xCenter <= x2 + radius && y1 <= yCenter && yCenter <= y2) {
            return true;
        }
        /* 矩形左上角 */
        if (distance(xCenter, yCenter, x1, y2) <= radius * radius)  {
            return true;
        }
        /* 矩形左下角 */
        if (distance(xCenter, yCenter, x1, y1) <= radius * radius) {
            return true;
        }
        /* 矩形右上角 */
        if (distance(xCenter, yCenter, x2, y2) <= radius * radius) {
            return true;
        }
        /* 矩形右下角 */
        if (distance(xCenter, yCenter, x1, y2) <= radius * radius) {
            return true;
        }
        /* 无交点 */
        return false;
    }

    public double distance(int ux, int uy, int vx, int vy) {
        return (long)Math.pow(ux - vx, 2.0) + Math.pow(uy - vy, 2.0);
    }
}
