package com.dinein.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dinein.model.Order;
import com.dinein.model.Reservation;

@Repository
public interface OrderRepository extends CrudRepository<Order, String>{

	Optional<Order> findTopByOrderByIdDesc();
	Optional<Order> findByOrderId(String id);

	Optional<List<Order>> findByCustId(String uid);
	List<Order> findAllByStaffId(String string);
	List<Order> findAllByStaffIdOrderByIdDesc(String string);

	
	Optional<List<Order>> findByCustIdOrderByIdDesc(String string);
	
	List<Order> findAllByOrderByIdDesc();

}
