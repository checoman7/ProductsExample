package Util;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;


public class BaseClass {
    protected RequestSpecification httpRequest;
    protected  String token;
    WireMockServer wireMockServer;

    @BeforeMethod(groups = {"regression"})
    public void setUp(){

        if(ConfigReader.isMocked()){
            wireMockServer = new WireMockServer(8080);
            wireMockServer.start();
            RestAssured.baseURI=ConfigReader.getMockedUrl();
            RestAssured.port = 8080;
            httpRequest= RestAssured.given();
            token = "asdfasdfasfasdf";
        }

        if(!ConfigReader.isMocked()) {
            RestAssured.baseURI = ConfigReader.getBaseURI();
            httpRequest = RestAssured.given();
            Response response = httpRequest.body("{\n" +
                    "   \"username\":\"mor_2314\",\n" +
                    "   \"password\":\"83r5^_\"\n" +
                    "}").contentType(ContentType.JSON).post("/auth/login");
            token = response.jsonPath().getString("token");
        }



    }

    @AfterMethod(groups = {"regression"})
    public void wireMockTearDown(){
        if (ConfigReader.isMocked())wireMockServer.stop();
    }


}
