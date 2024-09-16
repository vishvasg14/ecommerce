package com.example.demo.dto;

import java.math.BigDecimal;

import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResDto {

	
	private Long id;
	private Long orderNumber;
	private OrderStatus orderStatus;
	private PaymentStatus paymentStatus;
	private BigDecimal totalPrice;
	private String shippingAddress;
	private int itemCount;
}
