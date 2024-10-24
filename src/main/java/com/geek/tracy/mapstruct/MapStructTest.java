package com.geek.tracy.mapstruct;

import com.geek.tracy.mapstruct.bean.Order;
import com.geek.tracy.mapstruct.bean.OrderDTO;

public class MapStructTest {

    public static void main(String[] args) {
        Order saleOrder = Order.builder().id(1).name("销售订单").createTime("2024-10-24 19:30:00").updateTime("2024-10-24 19:30:00").build();
        System.out.println(saleOrder);
        OrderDTO orderDTO = MapperConverter.INSTANCE.orderToDto(saleOrder);
        System.out.println(orderDTO);
    }
}
