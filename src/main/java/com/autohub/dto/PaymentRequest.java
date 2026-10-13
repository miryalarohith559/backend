package com.autohub.dto;

public class PaymentRequest {
    private Long invoiceId;
    private double amount;
    private String paymentMethod; // UPI, CREDIT_CARD, DEBIT_CARD, CASH, NET_BANKING

    public PaymentRequest() {}

    public PaymentRequest(Long invoiceId, double amount, String paymentMethod) {
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
