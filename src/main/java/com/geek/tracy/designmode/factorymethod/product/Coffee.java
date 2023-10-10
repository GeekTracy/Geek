package com.geek.tracy.designmode.factorymethod.product;

/**
 * 抽象product：咖啡
 * @Author Tracy
 * @Date 2023/10/9
 */
public abstract class Coffee {

    private String name;

    /** 加奶 */
    public abstract void addMilk();

    /** 加糖 */
    public abstract void addSugar();

    public Coffee() {
    }

    public Coffee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "name='" + name + '\'' +
                '}';
    }
}
