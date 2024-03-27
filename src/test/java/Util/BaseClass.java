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

    @BeforeMethod(groups = {"regression","smoke","test"})
    public void setUp(){

        if (ConfigReader.isMocked() && !ConfigReader.isStandAlone())
            configInMocked();
        else if (!ConfigReader.isMocked() && ConfigReader.isStandAlone())
            configInMockedStandalone();
        else configInServer();
    }

    @AfterMethod(groups = {"regression", "smoke"})
    public void wireMockTearDown(){
        if (ConfigReader.isMocked())wireMockServer.stop();
    }

    public void configInMocked(){
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
        RestAssured.baseURI=ConfigReader.getMockedUrl();
        RestAssured.port = 8080;
        httpRequest= RestAssured.given();
        token = "mockedtoken";
    }

    public void configInMockedStandalone(){
            RestAssured.baseURI = ConfigReader.getMockedUrl();
            httpRequest = RestAssured.given();
    }

    public void configInServer(){
        RestAssured.baseURI = ConfigReader.getBaseURI();
        httpRequest = RestAssured.given();
        Response response = httpRequest.body("{\n" +
                "   \"username\":\"mor_2314\",\n" +
                "   \"password\":\"83r5^_\"\n" +
                "}").contentType(ContentType.JSON).post("/auth/login");
        token = response.jsonPath().getString("token");
    }

}
