package Api;

import SerializationPayloads.CreateProductPayload;
import SerializationPayloads.ProductResponse;
import Util.BaseClass;
import Util.SerializationManager;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class Products extends BaseClass {

    @Test
    public void singleProduct() throws Exception {
        Response resp = httpRequest.pathParam("id",1).get("/products/{id}").then().statusCode(200).extract().response();
        ProductResponse respAsObj = SerializationManager.deserialize(resp.getBody().asString(), ProductResponse.class);
        resp.then().statusCode(200);
        Assert.assertEquals(respAsObj.getId(),1);
        Assert.assertEquals(respAsObj.getTitle(), "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops");
        Assert.assertEquals(respAsObj.getPrice(),109.95);
    }
    @Test
    public void multipleProducts() throws Exception {
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
        Response resp = httpRequest.body(jsonProd).post("/products");
        resp.then().statusCode(200);

    }

}
