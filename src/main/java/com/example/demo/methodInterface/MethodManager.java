package com.example.demo.methodInterface;

import java.util.List;

import com.example.demo.dto.OrderReqDto;
import com.example.demo.dto.OrderResDto;
import com.example.demo.dto.ReqItemDto;
import com.example.demo.dto.ResItemDto;

public interface MethodManager {

	/*
	 * methods for Items endpoints 
	 */
	
	List<ResItemDto> getAllItems();
	
	ResItemDto getSpecificItems(Long itemId) throws Exception;
	
	String addNewItem(ReqItemDto reqItemDto);
	
	String updateSpecificItem(Long itemId, ReqItemDto reqItemDto);
	
	String deleteSpecificItem(Long itemId);
	
	/*
	 * methods for Order endpoints 
	 */

	String addNewOrders(OrderReqDto orderReqDto);
	
	List<OrderResDto> getAllOrders();
	
	OrderResDto getSpecificOrder(Long orderId);
	
	String updateSpecificOrders(Long orderId, OrderReqDto orderReqDto);
	
	String deleteSpecificOrders(Long orderId);
}
