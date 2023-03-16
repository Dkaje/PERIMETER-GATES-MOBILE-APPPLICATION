package com.example.perimetergates.Intel;

public class ReplyView {
    String customer = null;
    String counter = null;
    String date = null;
    String message = null;

    public ReplyView(String customer, String counter, String date, String message) {
        this.customer = customer;
        this.counter = counter;
        this.date = date;
        this.message = message;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return customer + " " + date + " " + message;
    }
}
