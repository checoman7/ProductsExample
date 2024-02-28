package Api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PetsApi {


    @Test(priority = 0)
    public void agetPetByIDNonExistingPet(){
        RestAssured.baseURI = "https://petstore3.swagger.io/api/v3";
        Response response =RestAssured.given().pathParam("petId", 1457).get("/pet/{petId}");
        Assert.assertTrue(response.getStatusCode() ==404);
        System.out.println(response.getStatusCode());
        System.out.println(response.prettyPrint());


    }

    @Test(priority = 0)
    public void getPetByIDExistingPet(){
        RestAssured.baseURI = "https://petstore3.swagger.io/api/v3";
        Response response =RestAssured.given().pathParam("petId", 1732).get("/pet/{petId}");
        Assert.assertTrue(response.getStatusCode() ==200);
        System.out.println(response.getStatusCode());
        System.out.println(response.prettyPrint());
        System.out.println(response.jsonPath().getString("name"));




    }

}
