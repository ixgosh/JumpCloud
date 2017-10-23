package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import requests.HerokuApi;

//Test will send GET stats request and print result
public class GetStatsTest {

    @Test
    public void getStatsTest(){

        //Get stats
        Response getStats = new HerokuApi().getStatsRequest();
        Assert.assertEquals(200, getStats.getStatusCode());

        //Parse results
        JsonPath jsonPath = new JsonPath( getStats.body(). asString());

        Integer totalRequests = jsonPath.getInt("TotalRequests");
        Integer averageTime = jsonPath.getInt("AverageTime");

        //Print results
        System.out.println("Requests: " + totalRequests);
        System.out.println("Time: " + averageTime);

    }
}
