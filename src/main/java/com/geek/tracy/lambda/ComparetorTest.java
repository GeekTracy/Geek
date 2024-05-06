package com.geek.tracy.lambda;


import com.geek.tracy.bean.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yang
 * @Date 2022/2/24
 */
public class ComparetorTest {


    /**
     * List<类> list; 代表某集合
     *
     * //返回 对象集合以类属性一升序排序
     * list.stream().sorted(Comparator.comparing(类::属性一));
     *
     * //返回 对象集合以类属性一降序排序 注意两种写法
     * list.stream().sorted(Comparator.comparing(类::属性一).reversed());//先以属性一升序,结果进行属性一降序
     * list.stream().sorted(Comparator.comparing(类::属性一,Comparator.reverseOrder()));//以属性一降序
     *
     * //返回 对象集合以类属性一升序 属性二升序
     * list.stream().sorted(Comparator.comparing(类::属性一).thenComparing(类::属性二));
     *
     * //返回 对象集合以类属性一降序 属性二升序 注意两种写法
     * list.stream().sorted(Comparator.comparing(类::属性一).reversed().thenComparing(类::属性二));//先以属性一升序,升序结果进行属性一降序,再进行属性二升序
     * list.stream().sorted(Comparator.comparing(类::属性一,Comparator.reverseOrder()).thenComparing(类::属性二));//先以属性一降序,再进行属性二升序
     *
     * //返回 对象集合以类属性一降序 属性二降序 注意两种写法
     * list.stream().sorted(Comparator.comparing(类::属性一).reversed().thenComparing(类::属性二,Comparator.reverseOrder()));//先以属性一升序,升序结果进行属性一降序,再进行属性二降序
     * list.stream().sorted(Comparator.comparing(类::属性一,Comparator.reverseOrder()).thenComparing(类::属性二,Comparator.reverseOrder()));//先以属性一降序,再进行属性二降序
     *
     * //返回 对象集合以类属性一升序 属性二降序 注意两种写法
     * list.stream().sorted(Comparator.comparing(类::属性一).reversed().thenComparing(类::属性二).reversed());//先以属性一升序,升序结果进行属性一降序,再进行属性二升序,结果进行属性一降序属性二降序
     * list.stream().sorted(Comparator.comparing(类::属性一).thenComparing(类::属性二,Comparator.reverseOrder()));//先以属性一升序,再进行属性二降序
     *
     * //空/Null数据排序
     * list.stream().sorted(Comparator.comparing(类::属性一).thenComparing(item -> item.属性二, Comparator.nullsLast(Date::compareTo))).collect(Collectors.toList());
     *
     * //空/Null数据分组
     * Map<String, List<类>> map = list.stream().collect(Collectors.groupingBy(item -> {
     *   if (item.属性一 == null || item.属性一.equals("")) {
     *   　　return "";
     *   }
     *   return DateFormat.getDateInstance().format(item.属性一);
     * }))
     */
    public static void main(String[] args) {
        Order order1 = new Order("1101", "2019-11-03 00:30:31", 100);
        Order order2 = new Order("1102", "2019-11-03 00:30:32", 200);
        Order order3 = new Order("1103", "2019-11-03 00:30:33", 300);
        Order order4 = new Order("1104", "2019-11-03 00:30:34", 400);
        Order order5 = new Order("1105", "2019-11-03 00:30:35", 500);
        List<Order> orders = new ArrayList<>();
        orders.add(order2);
        orders.add(order1);
        orders.add(order4);
        orders.add(order3);
        orders.add(order5);
        System.out.println("------------排序前-----------------");
        for (Order order : orders) {
            System.out.println("orderNum=" + order.getOrderNum() + ",payTime=" + order.getPayTime());
        }
        //不管是Date、String、Long类型的日期都可以排序，无需转换
        orders.sort((t1, t2) -> t2.getPayTime().compareTo(t1.getPayTime()));
        System.out.println("------------倒序后-----------------");
        for (Order order : orders) {
            System.out.println("orderNum=" + order.getOrderNum() + ",payTime=" + order.getPayTime());
        }
    }
}
