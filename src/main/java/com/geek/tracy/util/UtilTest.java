package com.geek.tracy.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Tracy
 * @Date 2023/10/12
 */
public class UtilTest {


    public List<String> numDecodings(String s) {
        List<String> result = new ArrayList<>();
        if (s == null || s.length() == 0 || s.charAt(0) == '0') {
            return result;
        }

        dfs(s, 0, new StringBuilder(), result);
        return result;
    }

    private void dfs(String s, int index, StringBuilder current, List<String> result) {
        if (index == s.length()) {
            result.add(current.toString());
            return;
        }

        // 尝试一个字符的解码
        if (s.charAt(index) != '0') {
            current.append((char) ('a' + (s.charAt(index) - '1')));
            dfs(s, index + 1, current, result);
            current.deleteCharAt(current.length() - 1); // 回溯
        }

        // 尝试两个字符的解码（如果可行）
        if (index + 1 < s.length() && (s.charAt(index) == '1' || (s.charAt(index) == '2' && s.charAt(index + 1) <= '6'))) {
            int twoDigitNum = (s.charAt(index) - '0') * 10 + (s.charAt(index + 1) - '0');
            current.append((char) ('a' + (twoDigitNum - 11)));
            dfs(s, index + 2, current, result);
            current.deleteCharAt(current.length() - 1); // 回溯
        }
    }

    @Test
    public void test1111() {
        List<String> result = numDecodings("111");
        System.out.println(result); // 输出: [aa, k]

        result = numDecodings("113");
        System.out.println(result); // 输出: [aac, kc, am]
    }
    /**
     * 最大收益，计算dp[]数组，本体6块土地总油耗：25，
     *
     */
    public int[] maxIncome2() {
        // 土地二维数组，第一列表示油耗，第二列表示收益
        int[][] land = new int[][]{{1, 1},{7, 9},{6, 10},{2, 4},{3, 5},{6, 10}};
        // 所有土地总油耗
        int oilSum = Arrays.stream(land).mapToInt(item -> item[0]).sum();
        // 定义dp数组，下标表示油耗，值表示收益，其中下标为0值也为0
        int[] dp = new int[oilSum + 1];
        // dp[n] = Max(dp[n-1] + dp[1], land[n])
        for (int i = 1; i < dp.length; i++) {

        }
        return new int[0];
    }

    @Test
    public void testMaxIncome() {
        for (int i = 0; i < 30; i++) {
            System.out.println("总油耗：" + i + ", 最大收益" + maxIncome(i));
        }
    }
    /**
     * 记录最大收益
     */
    private static Integer MaxIncome = 0;
    /**
     * 回溯法，遍历所有的可能情况，算出汽车油耗范围内最大的收益
     *
     * @param carOil 汽车油量
     */
    public int maxIncome(int carOil) {
        // 土地二维数组，第一列表示油耗，第二列表示收益
        int[][] land = new int[][]{{1, 1},{7, 9},{6, 10},{2, 4},{3, 5},{6, 10}};
        // 所有土地总油耗
        int landOilSum = Arrays.stream(land).mapToInt(item -> item[0]).sum();
        // 汽车耗油量大于等于所有土地油耗，活全干了收益最大
        if (carOil >= landOilSum) {
            return Arrays.stream(land).mapToInt(item -> item[1]).sum();
        }
        // 按油耗升序排序
        Arrays.sort(land, Comparator.comparingInt(item -> item[0]));
        // 回溯法遍历所有土地的组合，使油耗小于等于汽车油耗时的最大收益
        backSource(new ArrayList<>(), carOil, land);
        return MaxIncome;
    }

    /**
     * 回溯遍历可能的情况，当没有可继续耕种的土地时，计算最大收益MaxIncome
     * @param landNum 土地二维数组的下边，记录耕种的土地下标index
     * @param carOilSum 车拥有的油量
     * @param land 土地二维数组
     */
    private void backSource(List<Integer> landNum, int carOilSum, int[][] land) {
        // 当前油耗
        int currOil = landNum.stream().mapToInt(index -> land[index][0]).sum();
        // 剩下的油
        int carLeftOil = carOilSum - currOil;
        if (carLeftOil == 0) {
            // 计算收益
            MaxIncome = Math.max(MaxIncome, landNum.stream().mapToInt(index -> land[index][1]).sum());
            return;
        }
        if (carLeftOil > 0) {
            boolean firstOne = true;
            // 遍历未使用的土地
            for (int i = 0; i < land.length; i++) {
                // 耕种过的土地，跳过
                if (landNum.contains(i)) {
                    continue;
                } else if (firstOne && land[i][0] > carLeftOil) {
                    // 因为土地已按照耗油量排序，遍历时第一个就大于车剩下的油量，已不再有可处理的地，直接返回并计算最大收益
                    MaxIncome = Math.max(MaxIncome, landNum.stream().mapToInt(index -> land[index][1]).sum());
                    return;
                } else if (land[i][0] <= carLeftOil) {
                    firstOne = false;
                    // 增加处理土地
                    landNum.add(i);
                    // 下钻处理
                    backSource(landNum, carOilSum, land);
                    // 回退
                    landNum.remove(landNum.size() - 1);
                }
            }
        }
    }


    @Test
    public void testDecode() {
        System.out.println("113" + " 可能的译码结果：");
        decode("113").stream().forEach(item -> System.out.print(item + " "));
        System.out.println("\r\n121232" + " 可能的译码结果：");
        decode("121232").stream().forEach(item -> System.out.print(item + " "));
        System.out.println("\r\n2354689512156532456" + " 可能的译码结果：");
        decode("2354689512156532456").stream().forEach(item -> System.out.print(item + " "));
    }

    /**
     * 编码字典
     */
    public static final Map<String, String> DIC = new HashMap<String, String>(){{
       put("1", "a");
       put("2", "b");
       put("3", "c");
       put("4", "d");
       put("5", "e");
       put("6", "f");
       put("7", "g");
       put("8", "h");
       put("9", "i");
       put("10", "j");
       put("11", "k");
       put("12", "l");
       put("13", "m");
       put("14", "n");
       put("15", "o");
       put("16", "p");
       put("17", "q");
       put("18", "r");
       put("19", "s");
       put("20", "t");
       put("21", "u");
       put("22", "v");
       put("23", "w");
       put("24", "x");
       put("25", "y");
       put("26", "z");
    }};
    /**
     * 解码
     * 动态规划：f(n)=f(n-1) + f(n-2)，
     * 1）其中f(n-1)代表code前n-1个字符解码拼接code最后一位字符映射的字母得到的集合；
     * 2）其中f(n-2)代表code前n-2个字符解码拼接code最后2位字符映射的字母得到的集合（若最后2位字符大于26，则必须将最后2位字符拆开，此时结果与f(n - 1)重复，f(n - 2)返回空）；
     *
     * @param code 不超过20位 （仅考虑code为1-9的数字，未考虑0）
     */
    public List<String> decode(String code) {
        int length = code.length();
        if (length == 1) {
            return new ArrayList<>(Arrays.asList(DIC.get(code)));
        }
        if (length == 2) {
            List<String> ans = new ArrayList<>();
            ans.add(DIC.get(code.substring(0, 1)) + DIC.get(code.substring(1, 2)));
            if (DIC.get(code) != null) {
                ans.add(DIC.get(code));
            }
            return ans;
        }
        // f(n - 1) + f(n - 2)
        List<String> f1 = merge(decode(code.substring(0, length - 1)), DIC.get(code.substring(length - 1, length)));
        List<String> f2 = merge(decode(code.substring(0, length - 2)), DIC.get(code.substring(length - 2, length)));
        f1.addAll(f2);
        return f1;
    }

    /**
     * list 中解码的每一个字符拼接最后一个或两个字符映射的编码字符，若cha为空，则将返回空
     */
    private List<String> merge(List<String> list, String cha) {
        if (null == cha || "".equals(cha)){
            return new ArrayList<>(0);
        }
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i) + cha);
        }
        return list;
    }

    @Test
    public void test() {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13);
        System.out.println("数组分区大小：5，分区结果为：\r\n" + ListUtil.partition(list, 5));

        System.out.println("+86-13823597895".contains("13823597895"));
    }

}
