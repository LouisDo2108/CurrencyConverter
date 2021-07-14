package com.example.CurrencyConverter;

public class ParseItem {
    private String exchange, price, dayChange, weeklyChange, monthlyChange, date;

    public ParseItem(String exchange, String price, String dayChange, String weeklyChange, String monthlyChange, String date) {
        this.exchange = exchange;
        this.price = price;
        this.dayChange = dayChange;
        this.weeklyChange = weeklyChange;
        this.monthlyChange = monthlyChange;
        this.date = date;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDayChange() {
        return dayChange;
    }

    public void setDayChange(String dayChange) {
        this.dayChange = dayChange;
    }

    public String getWeeklyChange() {
        return weeklyChange;
    }

    public void setWeeklyChange(String weeklyChange) {
        this.weeklyChange = weeklyChange;
    }

    public String getMonthlyChange() {
        return monthlyChange;
    }

    public void setMonthlyChange(String monthlyChange) {
        this.monthlyChange = monthlyChange;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ParseItem(){
    }
}
