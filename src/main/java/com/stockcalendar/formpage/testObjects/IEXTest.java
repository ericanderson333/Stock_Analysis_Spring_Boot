package com.stockcalendar.formpage.testObjects;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/*
TEST OBJECT. NOT USED IN APPLICATION
 */

public class IEXTest {
    private static final String STOCK_BASE_URL = "https://cloud.iexapis.com/";
    private static final String secretKey = "IEX SECRET KEY HERE";
    private static final String publicKey = "IEX PUBLIC KEY HERE";
    private static final String ticker = "AAPL/";

    static public void main(String[] args) throws IOException, InterruptedException {
        /*
        String version = "stable/";
        String date = "20200601/";
        final String url = STOCK_BASE_URL + version + "stock/"
                + ticker + "daily" + "?token=" + secretKey;
        System.out.println(url);
        */
        final String url = "https://sandbox.iexapis.com/stable/stock/aapl/chart/date/20100111?chartByDay=true&token=Tsk_5dbb98f722c84395a93611da05182af3";



        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        //forbidden error. Fix later

    }
}
