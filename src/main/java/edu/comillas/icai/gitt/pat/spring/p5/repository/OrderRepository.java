package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.OrderDetail;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OrderRepository extends CrudRepository<Orders, Long>
{
    List<Orders> findByAppUser(AppUser appUser);
}