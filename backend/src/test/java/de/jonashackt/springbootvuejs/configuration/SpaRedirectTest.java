//package de.jonashackt.springbootvuejs.configuration;
//
//import de.jonashackt.springbootvuejs.SpringBootVuejsApplication;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.ValidatableResponse;
//import org.apache.http.HttpStatus;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//
//import static org.hamcrest.Matchers.containsString;
//
//@SpringBootTest(
//        classes = SpringBootVuejsApplication.class,
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
//)
//class SpaRedirectTest {
//
//    @LocalServerPort
//    private int port;
//
//    @BeforeEach
//    void setUp() {
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.port = port;
//    }
//
//    @Test
//    void it_should_not_interfere_with_api() {
//    }
//
//    @Test
//    void it_should_not_interfere_with_actuator() {
//    }
//}