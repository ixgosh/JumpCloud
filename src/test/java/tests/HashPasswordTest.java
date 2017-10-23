package tests;

import encryption.EncryptPassword;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import requests.HerokuApi;
import synchronisation.Wait;

//This test will encode three different passwords and verify encoding
public class HashPasswordTest {

    @Test
    public void hashPasswordTest() {

        String jobIdentifier;
        String passwordHash;
        String expectedpasswordHash;

        String[] passwords = {"angrymonkey", "1234567890", "~!@#$%^&*"};

        for (String password : passwords){

            //Get Job Identifier
            Response getJobIdentifier = new HerokuApi().getJobIdentifier(password);
            Assert.assertEquals(200, getJobIdentifier.getStatusCode());

            jobIdentifier = getJobIdentifier.body().asString();

            //wait 5 seconds to compute the password hash
            //Wait.aFewSeconds(5);

            //Use Job Identifier to get hashed password
            Response getHash = new HerokuApi().getPasswordHash(jobIdentifier);
            Assert.assertEquals(200, getHash.getStatusCode());

            passwordHash = getHash.body().asString();

            //Hash selected password with SHA512 and encode it with Base64 to get expected hash
            expectedpasswordHash = EncryptPassword.encryptPassword(password);

            //Verify that hashes are matching
            Assert.assertEquals("Hashed passwords must match", passwordHash, expectedpasswordHash);

        }
    }
}

