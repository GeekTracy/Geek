package com.geek.tracy.bean;

/**
 * @Author yang
 * @Date 2022/2/24
 */
public class Order {

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 付款时间
     */
    private String payTime;

    /**
     * 支付金额
     */
    private Integer actualAmount;

    public Order(String orderNum, String payTime, Integer actualAmount) {
        this.orderNum = orderNum;
        this.payTime = payTime;
        this.actualAmount = actualAmount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Integer getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Integer actualAmount) {
        this.actualAmount = actualAmount;
    }
}
