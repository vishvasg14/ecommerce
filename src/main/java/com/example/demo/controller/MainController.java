package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderReqDto;
import com.example.demo.dto.OrderResDto;
import com.example.demo.dto.ReqItemDto;
import com.example.demo.dto.ResItemDto;
import com.example.demo.dto.Response;
import com.example.demo.methodInterface.MethodManager;

import jakarta.websocket.server.PathParam;



@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	private MethodManager methodManager;
	
	
	/*
	 * API end Points for item CRUD operation
	 */
	
	
	@GetMapping("/items")
	public List<ResItemDto> getAllItems() {
		List<ResItemDto> res =methodManager.getAllItems();
		return res;
	}
	
	@GetMapping("/items/{itemId}")
	public ResponseEntity<ResItemDto> getOneItem(@PathParam("itemId") Long itemId)throws Exception {
		ResItemDto resItemDto = methodManager.getSpecificItems(itemId);
		return new ResponseEntity<>(resItemDto, HttpStatus.OK);
	}
	
	@PostMapping("/items")
	public Response addItem(@RequestBody ReqItemDto reqItemDto) {
		String res = methodManager.addNewItem(reqItemDto);
		return new Response(res,200);
	}
	
	@PutMapping("/items/{itemId}")
	public Response updateSpecificItem(@PathParam("itemId") Long itemId, @RequestBody ReqItemDto reqItemDto) {
		
		String res = methodManager.updateSpecificItem(itemId, reqItemDto);
		
		return new Response(res,200);
	}
	
	@DeleteMapping("/items/{itemId}")
	public Response deleteSpecificItem(@PathParam("itemId") Long itemId) {
		String res = methodManager.deleteSpecificItem(itemId);
		return new Response(res,200);
	}
	
	/*
	 * API end points for Orders CURD operation
	 */
	
	@PostMapping("/orders")
	public Response addNewOrder(@RequestBody OrderReqDto orderReqDto) {
		String res = methodManager.addNewOrders(orderReqDto);
		
		return new Response(res,200);
	}
	
	@GetMapping("/orders")
	public List<OrderResDto> getAllOrders() {
		List<OrderResDto> listOfOrder = methodManager.getAllOrders();
		return listOfOrder;
	}
	
	@GetMapping("/orders/{orderId}")
	public OrderResDto getSpecificOrder(@PathParam("orderId") Long orderId) {
		OrderResDto order= methodManager.getSpecificOrder(orderId);
		return order;
	}
	
	@PutMapping("/orders/{orderId}")
	public Response updateSpecificOrder(@PathParam("orderId") Long orderId, @RequestBody OrderReqDto orderReqDto) {
		String res = methodManager.updateSpecificOrders(orderId,orderReqDto);
		
		return new Response(res,200);
	}
	
	@DeleteMapping("/orders/{orderId}")
	public Response deleteSpecificOrder(@PathParam("orderId") Long orderId) {
		String res =methodManager.deleteSpecificOrders(orderId);
		return new Response(res,200);
	}
}
