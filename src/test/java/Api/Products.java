package Api;

import SerializationPayloads.CreateProductPayload;
import SerializationPayloads.ProductResponse;
import Util.BaseClass;
import Util.ConfigReader;
import Util.SerializationManager;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class Products extends BaseClass {

    @Test(groups = {"regression", "smoke"})
    public void singleProduct() throws Exception {
        if (ConfigReader.isMocked()) {
            stubFor(get(urlEqualTo("/products/1"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withBodyFile("/MockResponses/SingleProduct.json")));
        }
        Response resp = httpRequest.cookie("Bearer " + token).pathParam("id",1).get("/products/{id}").then().statusCode(200).extract().response();
        System.out.println(RestAssured.baseURI);
        System.out.println(resp.getStatusCode());
        System.out.println(resp.prettyPrint());
        //ProductResponse respAsObj = SerializationManager.deserialize(resp.getBody().asString(), ProductResponse.class);

    }
    @Test(groups = {""})
    public void multipleProducts() throws Exception {
        if (ConfigReader.isMocked()) {
            stubFor(get(urlEqualTo("/products"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withBodyFile("MockResponses/MultipleProducts.json")));
        }

        Response resp = httpRequest.get("/products");
        TypeReference<List<ProductResponse>> typeReference = new TypeReference<>() {};
        List<ProductResponse> listaProductos = SerializationManager.deserializeParamObj(resp.getBody().asString(), typeReference);
        resp.then().statusCode(200);
        Assert.assertTrue(listaProductos.size()==20);

    }

    @Test
    public void postExample() throws Exception{

        CreateProductPayload newProd = new CreateProductPayload("Test", 2.0, "test", "", "men");
        String jsonProd = SerializationManager.serialize(newProd);
        Response resp = httpRequest.cookie("Bearer " + token).body(jsonProd).post("/products");
        resp.then().statusCode(200);

    }

}
