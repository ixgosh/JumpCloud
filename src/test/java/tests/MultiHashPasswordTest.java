package tests;

import encryption.EncryptPassword;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import requests.HerokuApi;
import synchronisation.Wait;

import java.util.Arrays;
import java.util.Collection;

//This test will encode three different passwords and verify encoding by
//executing three test instances simultaneously

@RunWith(value = Parameterized.class)
public class MultiHashPasswordTest {

    private Object herokuRequests;

    public MultiHashPasswordTest(Object herokuRequests) {
        this.herokuRequests = herokuRequests;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> passwords() {
        return Arrays.asList(
                new Object[][]{
                        {"angrymonkey"},
                        {"1234567890"},
                        {"~!@#$%^&*"}
                });
    }

    @Test
    public void hashPasswordTest() {

        String jobIdentifier;
        String passwordHash;
        String expectedpasswordHash;

        String password = this.herokuRequests.toString();

        for (int i=0; i<3; i++){

            //Get Job Identifier
            Response getJobIdentifier = new HerokuApi().getJobIdentifier(password);
            Assert.assertEquals(200, getJobIdentifier.getStatusCode());

            jobIdentifier = getJobIdentifier.body().asString();

            //Use Job Identifier to get hashed password
            Response getHash = new HerokuApi().getPasswordHash(jobIdentifier);
            Assert.assertEquals(200, getHash.getStatusCode());

            passwordHash = getHash.body().asString();

            //Hash selected password with SHA512 and encode it with Base64 to get expected hash
            expectedpasswordHash = EncryptPassword.encryptPassword(password);

            //Verify that hashes are matching
            Assert.assertEquals("Hashed passwords must match", passwordHash, expectedpasswordHash);


            switch (password){

                case "angrymonkey":
                    System.out.println("First password: " + this.herokuRequests + " Hash: " + passwordHash);
                    break;

                case "1234567890":
                    System.out.println("Second password: " + this.herokuRequests + " Hash: " + passwordHash);
                    break;

                case "~!@#$%^&*":
                    System.out.println("Third password: " + this.herokuRequests + " Hash: " + passwordHash);
                    break;

                default:
                    throw new RuntimeException("Unknown password: " + password);
            }
        }
    }
}

