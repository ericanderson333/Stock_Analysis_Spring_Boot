package com.stockcalendar.formpage.dto;

import com.stockcalendar.formpage.dto.StockDtoCustomVars.DateAndDecimal;

import java.math.BigDecimal;
import java.util.List;

/*
DTO Object used for receiving and sending result to View
 */
public class StockDto {
    private String ticker;
    private String startDate;
    private String endDate;
    private List<DateAndDecimal> priceChange;
    private List<DateAndDecimal> percentChange;
    private BigDecimal averagePercentChange;

    public StockDto(){}

    public StockDto(String ticker, String startDate, String endDate,
                    List<DateAndDecimal> priceChange, List<DateAndDecimal> percentChange, BigDecimal averagePercentChange) {
        this.ticker = ticker;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceChange = priceChange;
        this.percentChange = percentChange;
        this.averagePercentChange = averagePercentChange;
    }

    public String getTicker() {
        return ticker;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public List<DateAndDecimal> getPriceChange() {
        return priceChange;
    }

    public List<DateAndDecimal> getPercentChange() {
        return percentChange;
    }

    public BigDecimal getAveragePercentChange() {
        return averagePercentChange;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPriceChange(List<DateAndDecimal> priceChange) {
        this.priceChange = priceChange;
    }

    public void setPercentChange(List<DateAndDecimal> percentChange) {
        this.percentChange = percentChange;
    }

    public void setAveragePercentChange(BigDecimal averagePercentChange) {
        this.averagePercentChange = averagePercentChange;
    }


    @Override
    public String toString() {
        return "StockDto{" +
                "ticker='" + ticker + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", priceChange=" + priceChange +
                ", percentChange=" + percentChange +
                ", averagePriceChange=" + averagePercentChange +
                '}';
    }
}
