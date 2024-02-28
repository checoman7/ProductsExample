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

    @Test(groups = {"regression", "smoke"})
    public void singleProduct() throws Exception {



        Response resp = httpRequest.cookie("Bearer " + token).pathParam("id",1).get("/products/{id}").then().statusCode(200).extract().response();
        ProductResponse respAsObj = SerializationManager.deserialize(resp.getBody().asString(), ProductResponse.class);
        System.out.println(resp.prettyPrint());
    }
    @Test(groups = {"regression"})
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
        Response resp = httpRequest.cookie("Bearer " + token).body(jsonProd).post("/products");
        resp.then().statusCode(200);

    }

}
