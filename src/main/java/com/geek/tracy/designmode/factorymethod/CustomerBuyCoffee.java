package com.geek.tracy.designmode.factorymethod;

import com.geek.tracy.designmode.factorymethod.product.Coffee;

/**
 * 用户购买咖啡
 * @Author Tracy
 * @Date 2023/10/9
 */
public class CustomerBuyCoffee {

    /**
     * 模拟用户购买咖啡，进入咖啡店下单买咖啡
     * @param args
     */
    public static void main(String[] args) {
        // 获取咖啡店对象
        CoffeeShop coffeeShop = new CoffeeShop();
        // 下单拿铁
        Coffee latte = coffeeShop.orderLatte();
        System.out.println(latte);
        // 下单美式
        Coffee americano = coffeeShop.orderAmericano();
        System.out.println(americano);
    }
}
