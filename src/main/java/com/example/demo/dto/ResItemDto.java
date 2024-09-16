package com.example.demo.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResItemDto {

	private Long id;
	private String name;
	private BigDecimal price;
	private String desciption;
	private Long category_id;
	private String imagePath;
}
