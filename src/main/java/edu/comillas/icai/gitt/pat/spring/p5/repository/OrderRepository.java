package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.Orders;
import org.springframework.data.repository.CrudRepository;


public interface OrderRepository extends CrudRepository<Orders, Long>
{
}