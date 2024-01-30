package Api;

import SerializationPayloads.ProductResponse;
import Util.BaseClass;
import Util.SerializationManager;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class Products extends BaseClass {

    @Test
    public void singleProduct() throws Exception {
        Response resp = httpRequest.pathParam("id",1).get("/products/{id}").then().statusCode(200).extract().response();
        ProductResponse respAsObj = SerializationManager.deserialize(resp.getBody().asString(), ProductResponse.class);

        System.out.print(respAsObj.getId() + ": ");
        System.out.println(respAsObj.getTitle());

    }
    @Test
    public void multipleProducts() throws Exception {
        Response resp = httpRequest.get("/products");
        TypeReference<List<ProductResponse>> typeReference = new TypeReference<List<ProductResponse>>() {};
        List<ProductResponse> listaProductos = SerializationManager.deserializeParamObj(resp.getBody().asString(), typeReference);

        listaProductos.forEach(product -> {
            System.out.print(product.getId()+ ": ");
            System.out.println(product.getTitle());
        });
    }

}
