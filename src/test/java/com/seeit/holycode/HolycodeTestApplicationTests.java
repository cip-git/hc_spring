package com.seeit.holycode;

import com.seeit.holycode.model.AuthenticationResponse;
import com.seeit.holycode.model.locations.PlaceLocalEntry;
import com.seeit.holycode.service.PlacesService;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryRegistry;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class HolycodeTestApplicationTests {

    @BeforeAll
    public static void setup() {
        baseURI = "http://localhost:8080";
    }

    @Autowired
    RetryRegistry retryRegistry;

    @Test
    public void shouldReturn200() throws JSONException {
        places("GXvPAor1ifNfpF0U5PTG0w")
                .statusCode(200);
    }

    private ValidatableResponse places(String id) throws JSONException {
        return given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt())
                .pathParam("id", id)
                .when()
                .log().all()
                .get("/places/{id}")
                .then()
                .log().all()
                .assertThat();
    }

    private String jwt() throws JSONException {
        var params = new JSONObject();
        params.put("username", "user");
        params.put("password", "user");

        return given()
                .contentType(ContentType.JSON)
                .body(params.toString())
                .when()
                .post(format("%s/auth", baseURI))
                .then()
                .assertThat()
				.statusCode(200)
                .extract()
                .body()
                .as(AuthenticationResponse.class)
                .getJwt();
    }
}


