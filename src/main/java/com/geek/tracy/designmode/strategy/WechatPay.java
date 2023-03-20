package com.geek.tracy.designmode.strategy;

/**
 * @Author Tracy
 * @Date 2023/3/1
 */
public class WechatPay implements PayStrategy {
    @Override
    public void pay() {
        System.out.println("微信支付中...");
    }
}
