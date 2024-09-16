package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReqDto {
	

	
	private String shippingAddress;
	private Long item_id;
	private int itemCount;
	
}
