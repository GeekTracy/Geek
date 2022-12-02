package com.geek.tracy.lambda;


import com.geek.tracy.bean.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yang
 * @Date 2022/2/24
 */
public class ComparetorTest {

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
