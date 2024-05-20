package edu.comillas.icai.gitt.pat.spring.p5.repository;


import edu.comillas.icai.gitt.pat.spring.p5.entity.Product;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, Long>
{
    Product findByProductName(String productName);
}