package com.example.restassureddemo;

import io.restassured.http.ContentType;
import org.springframework.util.Assert;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.Matchers.hasSize;

public class myFirstRestAssuredClass {
    final static String url="http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1";
    final static String irl="http://demo.guru99.com/V4/sinkministatement.php";
    final static long responseTimeThreshold = 500; // milliseconds
    final static int successfulStatusCode = 200;

    public static void main(String args[]) {
    	getTesting();
        getResponseStatus();
        getResponseTime();
    }

    public static void getTesting() {
    	given()
                .pathParam("raceSeason","2017")
                .when()
                .get("http://ergast.com/api/f1/{raceSeason}/circuits.json")
                .then()
                .assertThat()
                    .statusCode(200)
                .and()
                    .contentType(ContentType.JSON).
                and()
                    .body("MRData.CircuitTable.Circuits.circuitId",hasSize(20));
    }
    
    public static void getResponseStatus(){
        int statusCode= given().queryParam("CUSTOMER_ID","68195")
                .queryParam("PASSWORD","1234!")
                .queryParam("Account_No","1")
                .when().get(irl).getStatusCode();
        System.out.println("The response status is "+statusCode);

        Assert.isTrue(statusCode == successfulStatusCode,
                "Status Code in incorrect, was expecting " + successfulStatusCode);
    }

    // Check to ensure response time is within acceptable limits.
    public static void getResponseTime()  {
        long responseTime = get(url).timeIn(MILLISECONDS);
        System.out.println("The time taken to fetch the response "+ responseTime + " milliseconds");

        Assert.isTrue(responseTime < responseTimeThreshold, "Response Time Exceeded");
    }
}
