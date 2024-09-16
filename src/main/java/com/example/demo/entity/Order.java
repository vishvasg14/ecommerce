package com.example.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_order")
public class Order {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private Long orderNumber;
	
	@ManyToOne
    @JoinColumn(name = "item_id")
	private Item item;
	
	@Column(nullable = false , columnDefinition = "ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING'")
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	private BigDecimal totalPrice;
	private String shippingAddress;
	
	@Column(nullable = false , columnDefinition = "ENUM('SUCCESS', 'FAILED', 'PENDING') DEFAULT 'PENDING'")
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	private int itemCount;


}
