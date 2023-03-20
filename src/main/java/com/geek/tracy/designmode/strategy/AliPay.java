package com.geek.tracy.designmode.strategy;

/**
 * @Author Tracy
 * @Date 2023/3/1
 */
public class AliPay implements PayStrategy {
    @Override
    public void pay() {
        System.out.println("支付宝支付中...");
    }
}
