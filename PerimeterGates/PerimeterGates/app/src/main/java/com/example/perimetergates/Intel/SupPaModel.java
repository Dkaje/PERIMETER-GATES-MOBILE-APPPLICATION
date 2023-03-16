package com.example.perimetergates.Intel;

public class SupPaModel {

    String supplier = null;
    String payment = null;
    String disburse = null;
//supplier,payment,disburse

    public SupPaModel(String supplier, String payment, String disburse) {
        this.supplier = supplier;
        this.payment = payment;
        this.disburse = disburse;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDisburse() {
        return disburse;
    }

    public void setDisburse(String disburse) {
        this.disburse = disburse;
    }

    public String toString() {
        return supplier + " " + payment + " " + disburse;
    }
}
