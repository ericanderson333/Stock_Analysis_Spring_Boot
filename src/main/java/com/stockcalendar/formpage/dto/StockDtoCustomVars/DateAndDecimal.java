package com.stockcalendar.formpage.dto.StockDtoCustomVars;

import java.math.BigDecimal;

/*
Custom Object including Date and BigDecimal Data Types
 */
public class DateAndDecimal {
    private BigDecimal num;
    private String date;

    public DateAndDecimal(){}

    public DateAndDecimal(BigDecimal num, String date) {
        this.num = num;
        this.date = date;
    }

    public BigDecimal getNum() {
        return num;
    }

    public String getDate() {
        return date;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DateAndDecimal{" +
                "num=" + num +
                ", date=" + date +
                '}';
    }
}
