package edu.comillas.icai.gitt.pat.spring.p5.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderRequestUnitTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidOrderRequest() {
        // Given ...
        Map<String, Integer> orders = new HashMap<>();
        orders.put("Merluza", 2);
        orders.put("Tinto", 3);
        orders.put("Sandía", 5);

        OrderRequest pedido = new OrderRequest(
                "caprichosa@email.com", "20/05/2024", orders);
        // When ...
        Set<ConstraintViolation<OrderRequest>> violations =
                validator.validate(pedido);
        // Then ...
        assertTrue(violations.isEmpty());
    }

    @Test
    public void tesRestaurantNull() {
        // Given ...
        Map<String, Integer> orders = new HashMap<>();
        orders.put("Merluza", 2);
        orders.put("Tinto", 3);
        orders.put("Sandía", 5);
        OrderRequest pedido = new OrderRequest(
                "", "20/05/2024", orders);
        // When ...
        Set<ConstraintViolation<OrderRequest>> violations =
                validator.validate(pedido);
        // Then ...
        assertTrue(!violations.isEmpty());
    }

    @Test
    public void testBadDate() {
        // Given ...
        Map<String, Integer> orders = new HashMap<>();
        orders.put("Merluza", 2);
        orders.put("Tinto", 3);
        orders.put("Sandía", 5);
        OrderRequest pedido = new OrderRequest(
                "caprichosa@email.com", "20/05/", orders);
        // When ...
        Set<ConstraintViolation<OrderRequest>> violations =
                validator.validate(pedido);
        // Then ...
        assertTrue(!violations.isEmpty());
    }
}

