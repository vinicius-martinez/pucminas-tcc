package br.edu.puc.sca.resource.frontend;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class EquipamentoResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/frontend/v1/equipamentos")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}