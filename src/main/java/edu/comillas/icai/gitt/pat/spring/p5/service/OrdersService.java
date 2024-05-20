package edu.comillas.icai.gitt.pat.spring.p5.service;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.OrderDetail;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Orders;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Product;
import edu.comillas.icai.gitt.pat.spring.p5.model.OrderRequest;
import edu.comillas.icai.gitt.pat.spring.p5.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class OrdersService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    ProductRepository productRepository;

    @Transactional
    public OrderRequest createOrder(OrderRequest orderRequest)
    {
        Orders orders = new Orders();
        orders.appUser = appUserRepository.findByEmail(orderRequest.restaurant());
        orders.orderDate = LocalDate.parse(orderRequest.date(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        long totalPrice = 0;

        for (Map.Entry<String, Integer> entry : orderRequest.orders().entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            Product product = productRepository.findByProductName(productName);
            if(product == null)
                return null;
            long unitPrice = product.productPrice;
            long productTotalPrice = unitPrice * quantity;
            totalPrice += productTotalPrice;
        }
        orders.totalPrice = totalPrice;
        orderRepository.save(orders);
        createOrderDetails(orderRequest, orders);
        return orderRequest;
    }

    public void createOrderDetails(OrderRequest orderRequest, Orders orders)
    {
        for (Map.Entry<String, Integer> entry : orderRequest.orders().entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            Product product = productRepository.findByProductName(productName);
            long unitPrice = product.productPrice;
            long productTotalPrice = unitPrice * quantity;
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.order = orders;
            orderDetail.product = product;
            orderDetail.quantity = (long) quantity;
            orderDetail.detailPrice = productTotalPrice;
            orderDetail.appUser = product.appUser;
            orderDetail.status = "Sin procesar";
            orderDetailRepository.save(orderDetail);
        }
    }

    public Iterable<Product> getProducts()
    {
        return productRepository.findAll();
    }

    public List<OrderDetail> getOrdersBySupplier(String providerEmail) {
        AppUser provider = appUserRepository.findByEmail(providerEmail);
        return orderDetailRepository.findByAppUserAndStatus(provider, "Sin procesar");
    }
    public List<Orders> getOrdersByRestaurant(String restaurantEmail) {
        AppUser restaurant = appUserRepository.findByEmail(restaurantEmail);
        return orderRepository.findByAppUser(restaurant);
    }
}
