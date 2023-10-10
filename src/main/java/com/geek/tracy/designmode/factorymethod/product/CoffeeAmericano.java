package com.geek.tracy.designmode.factorymethod.product;

/**
 * 具体产品类：美式咖啡
 * @Author Tracy
 * @Date 2023/10/9
 */
public class CoffeeAmericano extends Coffee {

    public CoffeeAmericano(String name) {
        super(name);
    }

    @Override
    public void addMilk() {
        System.out.println(super.getName() + " add milk");
    }

    @Override
    public void addSugar() {
        System.out.println(super.getName() + " add sugar");

    }
}
