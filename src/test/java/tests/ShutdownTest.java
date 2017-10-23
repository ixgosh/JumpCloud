package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import requests.HerokuApi;

//Test will shutdown application and verify result by sending GET stats request
public class ShutdownTest {

    @Test
    public void shutdownTest(){


        //Request shutdown
        Response shutItDown = new HerokuApi().shutdownRequest();
        Assert.assertEquals(200, shutItDown.getStatusCode());

        //Verify shutdown by getting stats
        Response getStatsAfterShutDown = new HerokuApi().getStatsRequest();
        Assert.assertEquals(200, shutItDown.getStatusCode());

        //Parse results
        JsonPath jsonPath = new JsonPath( getStatsAfterShutDown.body(). asString());

        Integer totalRequests = jsonPath.getInt("TotalRequests");
        Integer averageTime = jsonPath.getInt("AverageTime");

        //Verify that totals reset
        Assert.assertTrue(totalRequests == 0);
        Assert.assertTrue(averageTime == 0);

    }
}
