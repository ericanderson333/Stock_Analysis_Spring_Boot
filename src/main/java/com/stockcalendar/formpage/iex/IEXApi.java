package com.stockcalendar.formpage.iex;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IEXApi {
    private final static String baseUrl = "https://sandbox.iexapis.com/stable/"; //stable=>version
    private String ticker;
    private final static String view = "chart/";
    private String date;
    private final static String interval = "?chartByDay=true&";
    private final static String token = "token={ENTER TOKEN HERE & REMOVE BRACKETS}"; //publishable token

    public IEXApi(){}

    public IEXApi(String ticker, String date) {
        this.ticker = "stock/" + ticker + "/";
        this.date = "date/" + date;
    }

    public BigDecimal getPrice() throws IOException, InterruptedException, JSONException {
        final String url = baseUrl + ticker + view + date + interval + token;
        System.out.println(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //json data returns as jsonarray type. Cast to JSONObject...
        JSONArray jsonArray = new JSONArray(response.body());
        try {
            JSONObject rec = jsonArray.getJSONObject(0);
            return new BigDecimal(rec.getString("close"));
        }
        catch (Exception e){
            System.out.println("JSONException: Index is null\n" + "No data for given date");
            return new BigDecimal(-1);
        }
    }

    public static BigDecimal getPrice(String ticker, String date)
            throws IOException, InterruptedException, JSONException {
        String stockTicker = "stock/" + ticker + "/";
        String stockDate = "date/" + date;
        final String url = baseUrl + stockTicker + view + stockDate + interval + token;
        //Request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .build();
        //Response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //handle error...

        //To JSONArray
        JSONArray jsonArray = new JSONArray(response.body());
        //To Get Price Value
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(0); //only one element in the array
            return new BigDecimal(jsonObject.getString("close"));

        }
        catch(Exception e){
            //System.out.println("JSONException: Index is null\n" + "No data for given date");
            return new BigDecimal(-1);
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, JSONException {
        String ticker = "AAPL";
        String date = "20110204";
        BigDecimal price = IEXApi.getPrice(ticker, date);
        System.out.println(price);
    }
}
