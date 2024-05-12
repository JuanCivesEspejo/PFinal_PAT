package edu.comillas.icai.gitt.pat.spring.p5.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class RegisterRequestUnitTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidRequest() {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "Nombre", "nombre@email.com",
                Role.RESTAURANTE, "aaaaaaA1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNameNull()
    {
        // Given ...
        RegisterRequest registro = new RegisterRequest(
                "", "pepito@email.com",
                Role.RESTAURANTE, "bAl10INT27.7s");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(!violations.isEmpty());
    }

    @Test
    public void testBadPassword()
    {
        RegisterRequest registro = new RegisterRequest(
                "Manolo", "manolo123@email.com",
                Role.RESTAURANTE, "manolo1");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(!violations.isEmpty());
    }

    @Test
    public void testBadEmailFormat()
    {
        RegisterRequest registro = new RegisterRequest(
                "Juan", "juan.com",
                Role.RESTAURANTE, "conTRAsenia25");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(!violations.isEmpty());
    }

    @Test
    public void testWrongRole()
    {
        RegisterRequest registro = new RegisterRequest(
                "Juan", "juan@email.com",
                null, "conTRAsenia25");
        // When ...
        Set<ConstraintViolation<RegisterRequest>> violations =
                validator.validate(registro);
        // Then ...
        assertTrue(!violations.isEmpty());
    }
}