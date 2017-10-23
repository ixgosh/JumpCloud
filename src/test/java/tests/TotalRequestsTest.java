package tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import requests.HerokuApi;


//Test will send three POST requests and verify that Total requests count increased by 3
public class TotalRequestsTest {

    @Test
    public void totalRequestsTest(){

        String password = "angrymonkey";

        Integer totalRequests;
        Integer newTotalRequest;

        //Get current stats
        Response getStats = new HerokuApi().getStatsRequest();
        Assert.assertEquals(200, getStats.getStatusCode());

        //Parse results
        JsonPath jsonPath = new JsonPath( getStats.body(). asString());
        totalRequests = jsonPath.getInt("TotalRequests");

        //Send three POST requests
        for (int i=0; i<3; i++ ){

            Response getJobIdentifier = new HerokuApi().getJobIdentifier(password);
            Assert.assertEquals(200, getJobIdentifier.getStatusCode());
        }

        //Get latest stats
        getStats = new HerokuApi().getStatsRequest();
        Assert.assertEquals(200, getStats.getStatusCode());

        //Parse results
        jsonPath = new JsonPath( getStats.body(). asString());
        newTotalRequest = jsonPath.getInt("TotalRequests");

        //Verify that total requests count increased by 3
        Assert.assertEquals(3, newTotalRequest - totalRequests);

    }
}
