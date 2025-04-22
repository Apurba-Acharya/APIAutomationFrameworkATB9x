package com.apurba.in.base;

import com.apurba.in.Asserts.AssertActions;
import com.apurba.in.endpoints.apiConstants;
import com.apurba.in.modules.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;

public  class BaseTest {
    // CommonToAll Testcase
    // Base URL, Content Type - json - common


    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;


    @BeforeTest
    public void setUp() {

        // BASE URL, Content Type JSON
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();

        requestSpecification = RestAssured
                .given()
                .baseUri(apiConstants.base_url)
                .contentType(ContentType.JSON)
                .log().all();

//        requestSpecification = new RequestSpecBuilder()
//                .setBaseUri(apiConstants.base_url)
//                .addHeader("Content-Type", "application/json")
//                .build().log().all();

    }

    public String getToken() {
        requestSpecification = RestAssured
                .given()
                .baseUri(apiConstants.base_url)
                .basePath(apiConstants.AUTH_URL);

        // Setting the payload
        String payload = payloadManager.setAuthPayload();

        // Get the Token
        response = requestSpecification.contentType(ContentType.JSON).body(payload).when().post();
        // String Extraction
        String token = payloadManager.getTokenFromJSON(response.asString());

        return token;
    }
}