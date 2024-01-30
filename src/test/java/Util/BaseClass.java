package Util;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;



public class BaseClass {
    protected RequestSpecification httpRequest;
    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = ConfigReader.getBaseURI();
        httpRequest = RestAssured.given();
    }


}
