package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.OrderDetail;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long>
{
    List<OrderDetail> findByAppUserAndStatus(AppUser appUser, String status);
}