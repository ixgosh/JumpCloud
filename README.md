# JumpCloud exercise 


**To install:**

1. Clone repo and open project with pom.xml file
2. From ~/JumpCloud directory, run 'mvn clean compile'
3. 'mvn test -Dtest=suits.JumpCloudExerciseSuit.java' - will execute all tests in JumpCloudExerciseSuit.java suite

**Test plan:**

1. Verify POST request to get job identifier:
   * send request with port 80
   * send request with port 443
   * send successful request with correct body {"password":"angrymonkey"}
   * send multiple requests to verify job identifier incrementation
   * send request with empty body {}
   * send request with wrong key word  {"wrongkey":"angrymonkey"}
   * send request with missing password {"password":""}
   
2. Verify GET password hash request:
   * send request with port 80
   * send request with port 443
   * send successful request
   * verify that 'angrymonkey' password was hashed with algorithm SHA512 and encoded with Base64
   * send requests with different job identifiers, verify hash is the same
   * send multiple different passwords requests and verify that hash is different every time
   * send request with not existing job identifier, verify 'hash not found' error
   
3. Verify GET stats request:
   * send request with port 80
   * send request with port 443
   * send three POST requests, verify that Total requests equal three
      
4. Verify Shutdown request:
   * succesful shutdown request
   * send POST request, then shutdown request, verify that request finished successfully
   * send POST request, then shutdown request, then another POST verify that request rejected
   * after shutdown, send stats request, verify TotalRequests and AverageTime are set to 0 
   
   
**Issues found:**

1. POST request with empty body {} will return '200 Successful request'
2. POST request with wrong key word  {"wrongkey":"angrymonkey"} will return '200 Successful request'
3. POST request with missing password {"password":""} will return '200 Successful request'
4. POST request sent during shutdown will crash application
5. GET /hash request without job identifier will result in 'strconv.ParseInt: parsing "hash": invalid syntax' 

NOTE: A  POST  to  /hash  should accept a password; it should return a job identifier immediately; 
      it should then wait 5 seconds and compute the password hash. The hashing algorithm should be SHA512.
      Actual result - job identifier return takes approximately 4-5 seconds, after that GET request returns password
      hash immediately. 