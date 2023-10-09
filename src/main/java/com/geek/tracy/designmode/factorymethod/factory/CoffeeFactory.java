package com.geek.tracy.designmode.factorymethod.factory;

import com.geek.tracy.designmode.factorymethod.product.Coffee;

/**
 * 咖啡工厂接口，创建单一咖啡产品
 * @Author Tracy
 * @Date 2023/10/9
 */
public interface CoffeeFactory {

    /** 工厂方法：创建咖啡产品 */
    Coffee makeCoffee();
}
