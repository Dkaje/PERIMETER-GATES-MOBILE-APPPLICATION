package com.example.perimetergates.Intel;

public class EarnModel {
    //id,ind,bank,cheque,supplier,amount,reg_date
    String id = null;
    String ind = null;
    String mpesa = null;
    String supplier = null;
    String amount = null;
    String reg_date = null;

    public EarnModel(String id, String ind, String mpesa, String supplier, String amount, String reg_date) {
        this.id = id;
        this.ind = ind;
        this.mpesa = mpesa;
        this.supplier = supplier;
        this.amount = amount;
        this.reg_date = reg_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getMpesa() {
        return mpesa;
    }

    public void setMpesa(String mpesa) {
        this.mpesa = mpesa;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String toString() {
        return id + " " + ind + " " + mpesa +" " + supplier + " " + amount + " " + reg_date;
    }
}

