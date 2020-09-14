package eu.cise;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class MessageResourceTest {
    @Test
    public void it_post_a_string_message_to_resource() {
        given()
                .when()
                .contentType("text/plain")
                .body("my message")
                .post("/messages")
                .then()
                .statusCode(200)
                .body(is("RECV: my message"));
    }


}
