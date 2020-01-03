package webservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webservice.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
