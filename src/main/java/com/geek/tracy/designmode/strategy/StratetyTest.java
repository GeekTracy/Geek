package com.geek.tracy.designmode.strategy;

/**
 * @Author Tracy
 * @Date 2023/3/1
 */
public class StratetyTest {

    public static void payMoney(PayStrategy payStrategy) {
        payStrategy.pay();
    }

    public static void main(String[] args) {
        // 微信支付
        PayStrategy wechatPay = new WechatPay();
        payMoney(wechatPay);
        // 支付宝支付
        PayStrategy aliPay = new AliPay();
        payMoney(aliPay);
    }
}
