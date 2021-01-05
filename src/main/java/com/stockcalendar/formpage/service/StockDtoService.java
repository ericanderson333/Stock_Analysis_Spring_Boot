package com.stockcalendar.formpage.service;

import com.stockcalendar.formpage.dto.StockDto;
import com.stockcalendar.formpage.dto.StockDtoCustomVars.DateAndDecimal;
import com.stockcalendar.formpage.exception.EndDateNotFoundException;
import com.stockcalendar.formpage.exception.StartDateNotFoundException;
import com.stockcalendar.formpage.iex.IEXApi;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/*
Implements Back-end for Web App
Takes in StockDto Object and returns new StockDto with all data types computed
Or returns null object if the data cannot be accessed
 */
@Service
public class StockDtoService {

    /*
    generate(): Back-End Computational function
     */
    public static StockDto generate(StockDto stockDto) throws IOException, ParseException, JSONException, InterruptedException {
        //check if endDate exists
        Date endDate = getCorrectDate(stockDto.getEndDate());

        //get year
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String yearStr = dateFormat.format(endDate);

        //create date object for startDate
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(yearStr + "-" + stockDto.getStartDate());

        //Make sure year is an integer
        final int yearFinal = Integer.parseInt(yearStr);

        //Get ticker
        String ticker = stockDto.getTicker();

        //currently date objects. Convert into string format yyyyMMdd
        //for startDate Date object and endDate Date object
        final String startDateStr = convertDateToString(startDate);
        final String endDateStr = convertDateToString(endDate);

        /*
        We have currently:
        int year
        String ticker
        String startDateStr
        String endDateStr
         */


        //Create new list objects to set for StockDto object
        List<DateAndDecimal> priceChange = new ArrayList<DateAndDecimal>();
        List<DateAndDecimal> percentChange = new ArrayList<DateAndDecimal>();

        //will replace in initial declaration of year
        int year = yearFinal - 9;
        int flag = 0; //if zero, then return new object. if 1, return null

        //Iterate 10 times for 10 years
        for(int i = 0; i<10; ++i){
            //Returns correct year
            String tempStartDateStr = replaceComponent(year, yearFinal, startDateStr);
            String tempEndDateStr = replaceComponent(year, yearFinal, endDateStr);

            //get price calling static method
            BigDecimal startPrice = IEXApi.getPrice(ticker, tempStartDateStr);
            BigDecimal endPrice = IEXApi.getPrice(ticker, tempEndDateStr);

            /*
            try block to check given day. Throw exception if no data is found
             */
            try {
                if (startPrice.signum() < 0) {
                    throw new StartDateNotFoundException();
                }
                if (endPrice.signum() < 0) {
                    throw new EndDateNotFoundException();
                }
            }
            catch(StartDateNotFoundException e){
                //reset price with offset days
                startPrice = findOffsetPrice(tempStartDateStr, ticker);

            }
            catch(EndDateNotFoundException e){
                //reset price with offset days
                endPrice = findOffsetPrice(tempEndDateStr, ticker);
            }
            //check if data was returned with new days
            if (startPrice.signum() < 0 || endPrice.signum() < 0){
                flag = 1;
                break;
            }

            //Calc priceChange
            BigDecimal priceChangeDecimal = endPrice.subtract(startPrice);

            //Calc percent change. Use MATH Context for scaling divisor.
            MathContext mc = new MathContext(4);
            BigDecimal percentChangeDecimal = priceChangeDecimal.divide(startPrice, mc).movePointRight(2);

            //Append properties to list.
            priceChange.add(new DateAndDecimal(priceChangeDecimal, Integer.toString(year)));
            percentChange.add(new DateAndDecimal(percentChangeDecimal, Integer.toString(year)));

            ++year;
        }
        if(flag==1) return null;

        //find average percent change
        BigDecimal averagePercentChange = calcPercent(percentChange);

        //return new StockDto(ticker, startDateStr, endDateStr, priceChange, percentChange, averagePriceChange);
        return new StockDto(ticker, new SimpleDateFormat("MM-dd").format(startDate),
                new SimpleDateFormat("MM-dd").format(endDate), priceChange,
                percentChange, averagePercentChange);

    }
    /*
    Calculate average percent change ... possibly standard deviation later
     */
    public static BigDecimal calcPercent(List<DateAndDecimal> nums){
        BigDecimal totalAdded = new BigDecimal(0);
        for(DateAndDecimal d : nums){
            totalAdded = totalAdded.add(d.getNum());
        }
        MathContext mc = new MathContext(4);
        return totalAdded.divide(new BigDecimal(10), mc);
    }

    /*
    find the deviation for days spanning the weekend+holidays
     */
    public static BigDecimal findOffsetPrice(String date, String ticker) throws InterruptedException, JSONException, IOException {
        int dayOfDate = Integer.parseInt(date.substring(date.length()-2, date.length()));//-2 reps 2 element places
        final int dayOfDateFinal = dayOfDate;
        int flag = 0;
        BigDecimal result = null;
        //3 represent up to 3 days of an offset
        for(int i = 0; i < 3; ++i){
            if(dayOfDate >= 29){
                //minus
                dayOfDate -= 1;
            }
            else{
                //add
                dayOfDate += 1;
            }
            String tempDate = replaceComponent(dayOfDate, dayOfDateFinal, date);
            result = IEXApi.getPrice(ticker, tempDate);
            if(result.signum() > 0){
                flag = 1;
                break;
            }
        }
        if(flag == 0) return new BigDecimal(-1);
        return result;
    }

    /*
    date = date to be changed to | datefinal = reference to current date in str | str is date in yyyyMMdd format
     */
    public static String replaceComponent(int date, final int dateFinal, String str){
        String dateStr = Integer.toString(date);
        String dateFinalStr = Integer.toString(dateFinal);
        return str.replace(dateFinalStr, dateStr);
    }


    /*
    Offsets given year by one year or not depending on when the given End date is
     */
    public static Date getCorrectDate(String endDate) throws ParseException {
        //for given date
        int year = Calendar.getInstance().get(Calendar.YEAR); //current year
        String end = year + "-" + endDate;
        Date givenEndDate = new SimpleDateFormat("yyyy-MM-dd").parse(end);

        //for todays date
        LocalDate current = LocalDate.now();
        Date currentDate = java.sql.Date.valueOf(current);

        if(givenEndDate.after(currentDate)){
            year -= 1;
            return new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + endDate);
        }
        return givenEndDate;
    }

    /*
    Converts Date object into String obj
     */
    public static String convertDateToString(Date dateObj){
        DateFormat dateFormatted = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatted.format(dateObj).replace("-", "");
    }

    public static void main(String[] args) throws ParseException, IOException, JSONException, InterruptedException {
        StockDto stock = new StockDto();
        stock.setTicker("AAPL");
        stock.setEndDate("02-05");
        stock.setStartDate("01-05");
        StockDtoService test = new StockDtoService();
        StockDto result = test.generate(stock);
        System.out.println("Stock Ticker: " + result.getTicker() + "\n");
        System.out.println("Stock Start Date: " + result.getStartDate() + "\n");
        System.out.println("Stock End Date: " + result.getEndDate() + "\n");
        for(int i = 0; i<10; ++i){
            System.out.println("Price Change in " + result.getPriceChange().get(i).getDate()
            + " is: $" + result.getPriceChange().get(i).getNum());
            System.out.println("Percent Change in " + result.getPercentChange().get(i).getDate()
                    + " is: " + result.getPercentChange().get(i).getNum() + "%");
            System.out.println("");

        }
        System.out.println("Average Price Change: " + result.getAveragePercentChange() + "%");
    }

}
