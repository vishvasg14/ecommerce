package com.example.demo.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;

import jakarta.transaction.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT COUNT(o) > 0 FROM Order o WHERE o.item.id = :itemId")
	boolean existsByItemId(@Param("itemId") Long itemId);

	@Modifying
	@Transactional
	@Query("UPDATE Order o SET o.itemCount = o.itemCount + :itemCount, o.totalPrice = :totalprice WHERE o.item.id = :itemId")
    void incrementCount(@Param("itemId") Long itemId, @Param("itemCount") int itemCount, @Param("totalprice") BigDecimal totalprice);

	@Modifying
	@Transactional
	@Query("UPDATE Order o SET o.orderStatus = 'CANCELLED' WHERE o.id = :orderId AND o.orderStatus = 'PENDING'")
	void deleteOrder(@Param("orderId") Long orderId);

	@Modifying
	@Transactional
	@Query("UPDATE Order o SET o.orderStatus = :status WHERE o.id = :id AND o.orderStatus = 'PENDING'")
	void updateOrderStatus(@Param("id") Long id, @Param("status") OrderStatus status);

	@Modifying
	@Transactional
	@Query("UPDATE Order o SET o.orderStatus = :status , o.item = NULL WHERE o.item.id = :itemId")
	void modifyStatus(Long itemId, @Param("status") OrderStatus status);
}
