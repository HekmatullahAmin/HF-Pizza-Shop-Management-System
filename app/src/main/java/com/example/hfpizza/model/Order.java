package com.example.hfpizza.model;

public class Order {
    private int orderId;
    private long orderDate;
    private String orderNames, paymentMode;
    private double orderAmount;

    public Order() {
    }

    public Order(int orderId, long orderDate, String orderNames, String paymentMode, double orderAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderNames = orderNames;
        this.paymentMode = paymentMode;
        this.orderAmount = orderAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNames() {
        return orderNames;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setOrderNames(String orderNames) {
        this.orderNames = orderNames;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }
}
