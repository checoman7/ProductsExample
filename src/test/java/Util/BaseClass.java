package Util;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class BaseClass {
    protected RequestSpecification httpRequest;
    protected  String token;
    @BeforeMethod(groups = {"regression"})
    public void setUp(){


        RestAssured.baseURI = ConfigReader.getBaseURI();
        httpRequest = RestAssured.given();
        Response response= httpRequest.body("{\n" +
                "   \"username\":\"mor_2314\",\n" +
                "   \"password\":\"83r5^_\"\n" +
                "}").contentType(ContentType.JSON).post("/auth/login");



        token = response.jsonPath().getString("token");


    }




}
