package de.jonashackt.springbootvuejs.configuration;

import de.jonashackt.springbootvuejs.SpringBootVuejsApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(
        classes = SpringBootVuejsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class SpaRedirectTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void it_should_not_interfere_with_api() {
    }

    @Test
    void it_should_not_interfere_with_actuator() {
    }
}