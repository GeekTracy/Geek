package com.geek.tracy.util;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Tracy
 * @Date 2023/10/12
 */
public class UtilTest {

    @Test
    public void testDecode() {

        decode("113").stream().forEach(item -> System.out.print(item + " "));
        System.out.println("-------------");
        System.out.println("111111");
        decode("111111").stream().forEach(item -> System.out.print(item + " "));

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
