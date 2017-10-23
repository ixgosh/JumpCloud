package requests;

import io.restassured.response.Response;

import static domainobject.ExerciseEnv.getUrl;
import static io.restassured.RestAssured.given;

public class HerokuApi {

    public Response getStatsRequest() {
        return given(). contentType("application/json"). when(). get(getUrl() + "/stats"). andReturn();
    }

    public Response getJobIdentifier(String password) {
        return given().contentType("application/json")
                .body("{\"password\":\"" + password + "\"}")
                .when().post(getUrl() + "/hash")  .andReturn();
    }

    public Response getPasswordHash(String jobIdentifier) {
        return given().contentType("application/json").when().get(getUrl() + "/hash/" + jobIdentifier).andReturn();
    }

    public Response shutdownRequest() {
        return given(). contentType("application/json"). body("shutdown"). when(). post(getUrl() + "/hash"). andReturn();
    }
}
