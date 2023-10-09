package com.geek.tracy.designmode.factorymethod.factory;

import com.geek.tracy.designmode.factorymethod.product.Coffee;
import com.geek.tracy.designmode.factorymethod.product.CoffeeLatte;

/**
 * 拿铁 工厂方法，生产拿铁咖啡
 * @Author Tracy
 * @Date 2023/10/9
 */
public class CoffeeAmericanoFactory implements CoffeeFactory{
    @Override
    public Coffee makeCoffee() {
        return new CoffeeLatte("coffee americano");
    }
}
