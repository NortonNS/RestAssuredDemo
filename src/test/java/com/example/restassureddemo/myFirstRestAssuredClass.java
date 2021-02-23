package com.example.restassureddemo;

import org.springframework.util.Assert;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class myFirstRestAssuredClass {
    final static String url="http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1";
    final static long responseTimeThreshold = 100; // milliseconds
    final static int successfulStatusCode = 200;

    public static void main(String args[]) throws Exception {
        getResponseBody();
        getResponseStatus();
        getResponseTime();
    }

    //This will fetch the response body as is and log it. given and when are optional here
    public static void getResponseBody(){
        given().when().get(url).then().log()
                .all();

        given().queryParam("CUSTOMER_ID","68195")
                .queryParam("PASSWORD","1234!")
                .queryParam("Account_No","1")
                .when().get("http://demo.guru99.com/V4/sinkministatement.php").then().log().body();
    }

    public static void getResponseStatus(){
        int statusCode= given().queryParam("CUSTOMER_ID","68195")
                .queryParam("PASSWORD","1234!")
                .queryParam("Account_No","1")
                .when().get("http://demo.guru99.com/V4/sinkministatement.php").getStatusCode();
        System.out.println("The response status is "+statusCode);

        Assert.isTrue(statusCode == successfulStatusCode,
                "Status Code in incorrect, was expecting " + successfulStatusCode);
    }

    // Check to ensure response time is within acceptable limits.
    public static void getResponseTime() throws Exception {
        long responseTime = get(url).timeIn(MILLISECONDS);
        System.out.println("The time taken to fetch the response "+ responseTime + " milliseconds");

        Assert.isTrue(responseTime < responseTimeThreshold, "Response Time Exceeded");
    }
}
