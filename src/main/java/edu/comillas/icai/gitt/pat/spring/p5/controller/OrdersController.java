package edu.comillas.icai.gitt.pat.spring.p5.controller;

import edu.comillas.icai.gitt.pat.spring.p5.entity.Orders;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Product;
import edu.comillas.icai.gitt.pat.spring.p5.model.OrderRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.p5.service.OrdersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @PostMapping("/api/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderRequest makeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        try
        {
            orderRequest = ordersService.createOrder(orderRequest);
            if(orderRequest == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return orderRequest;

        } catch (DataIntegrityViolationException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/api/products")
    public Iterable<Product> getProducts() {
        return ordersService.getProducts();
    }
}
