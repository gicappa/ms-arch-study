package eu.cise;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@Testcontainers
public class IntegrationTest {

    @Container
    public static DockerComposeContainer compose =
            new DockerComposeContainer<>(new File("src/test/resources/test-compose.yml"))
                    .withExposedService("submission-agent", 8080,
                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
                    .withLocalCompose(true)
                    .withBuild(true);

    @Test
    public void it_transfers_the_message() {
        given()
                .when()
                .contentType("text/plain")
                .body("my message")
                .post(composeUrl() + "/messages")
                .then()
                .statusCode(200)
                .body(is("RECV: my message"));
    }

    @NotNull
    private String composeUrl() {
        String host = compose.getServiceHost("submission-agent", 8080);
        Integer port = compose.getServicePort("submission-agent", 8080);
        return "http://" + host + ":" + port;
    }
}
