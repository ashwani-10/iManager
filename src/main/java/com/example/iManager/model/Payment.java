package com.example.iManager.model;

import com.example.iManager.enums.PaymentStatus;

import java.time.LocalDateTime;

public class Payment {
    private String orderId; // Razorpay Order ID

    private String orgData; // Store OrgRequestDTO as JSON

    private PaymentStatus status; // "PENDING", "PAID", "FAILED"

    private LocalDateTime created_At = LocalDateTime.now();

    public Payment(String orderId, String orgData,
                   PaymentStatus status, LocalDateTime created_At) {
        this.orderId = orderId;
        this.orgData = orgData;
        this.status = status;
        this.created_At = created_At;
    }

    public Payment() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrgData() {
        return orgData;
    }

    public void setOrgData(String orgData) {
        this.orgData = orgData;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreated_At() {
        return created_At;
    }

    public void setCreated_At(LocalDateTime created_At) {
        this.created_At = created_At;
    }
}
