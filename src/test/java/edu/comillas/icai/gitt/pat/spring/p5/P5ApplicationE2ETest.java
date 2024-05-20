package edu.comillas.icai.gitt.pat.spring.p5;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.OrderRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class P5ApplicationE2ETest {

    private static final String NAME = "Name";
    private static final String EMAIL = "name@email.com";
    private static final String PASS = "aaaaaaA1";
    private static final String RESTAURANT = "caprichosa@email.com";
    private static final String DATE = "20/05/2024";

    @Autowired
    TestRestTemplate client;

    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    AppUserRepository appUserRepository;

    @Test
    public void registerTest() {
        // Given ...
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String registro = "{" +
                "\"name\":\"" + NAME + "\"," +
                "\"email\":\"" + EMAIL + "\"," +
                "\"role\":\"" + Role.RESTAURANTE + "\"," +
                "\"password\":\"" + PASS + "\"}";

        // When ...
        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/api/users",
                HttpMethod.POST, new HttpEntity<>(registro, headers), String.class);

        // Then ...
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("{" +
                        "\"name\":\"" + NAME + "\"," +
                        "\"email\":\"" + EMAIL + "\"," +
                        "\"role\":\"" + Role.RESTAURANTE + "\"}",
                response.getBody());
    }

    @Test
    public void loginOkTest() {
        // Given ...
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String registro = "{" +
                "\"name\":\"" + NAME + "\"," +
                "\"email\":\"" + EMAIL + "\"," +
                "\"role\":\"" + Role.RESTAURANTE + "\"," +
                "\"password\":\"" + PASS + "\"}";

        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/api/users",
                HttpMethod.POST, new HttpEntity<>(registro, headers), String.class);

        //When ...
        String login = "{" +
                "\"email\":\"" + EMAIL + "\"," +
                "\"password\":\"" + PASS + "\"}";

        ResponseEntity<String> userResponse = client.exchange(
                "http://localhost:8080/api/users/me/session",
                HttpMethod.POST, new HttpEntity<>(login, headers), String.class);

        // Then ...
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(userResponse.getHeaders().get("Set-Cookie"));
    }

    @Test
    public void createOrder() {
        // Given ...
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String pedido = "{" +
                "\"restaurant\":\"" + RESTAURANT + "\"," +
                "\"date\":\"" + DATE + "\"," +
                "\"orders\":{\"Merluza\":2,\"Tinto\":3,\"Sandía\":5}" + "}";

        //When ...
        ResponseEntity<String> response = client.exchange(
                "http://localhost:8080/api/orders",
                HttpMethod.POST, new HttpEntity<>(pedido, headers), String.class);

        // Then ...
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        String expectedResponse = "{" +
                "\"restaurant\":\"" + RESTAURANT + "\"," +
                "\"date\":\"" + DATE + "\"," +
                "\"orders\":{\"Merluza\":2,\"Tinto\":3,\"Sandía\":5}" +
                "}";
        Assertions.assertEquals(expectedResponse, response.getBody());

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode actualResponseJson = objectMapper.readTree(response.getBody());
            JsonNode ordersNode = actualResponseJson.get("orders");

            Map<String, Integer> expectedOrders = new HashMap<>();
            expectedOrders.put("Merluza", 2);
            expectedOrders.put("Tinto", 3);
            expectedOrders.put("Sandía", 5);

            Assertions.assertEquals(expectedOrders.size(), ordersNode.size(), "The number of items in the order should match");

            for (Map.Entry<String, Integer> entry : expectedOrders.entrySet()) {
                Assertions.assertTrue(ordersNode.has(entry.getKey()), "Order should contain item: " + entry.getKey());
                Assertions.assertEquals(entry.getValue().intValue(), ordersNode.get(entry.getKey()).asInt(), "Order quantity for " + entry.getKey() + " should match");
            }

        } catch (Exception e) {
            Assertions.fail("Failed to parse JSON response: " + e.getMessage());
        }
    }
}

