package com.example.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderReqDto;
import com.example.demo.dto.OrderResDto;
import com.example.demo.dto.ReqItemDto;
import com.example.demo.dto.ResItemDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.PaymentStatus;
import com.example.demo.methodInterface.MethodManager;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;

@Service
public class MainService implements MethodManager {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	/*
	 * methods for Items endpoints 
	 */
	
	public List<ResItemDto> getAllItems() {
		List<ResItemDto> allItems = itemRepository.findListOfItem();
		return allItems;
	}

	public ResItemDto getSpecificItems(Long itemId) throws Exception {
		Optional<Item> optionalItem = itemRepository.findById(itemId);

		if (!optionalItem.isPresent()) {
			throw new Exception("Item not found for id: " + itemId);
		}

		Item item = optionalItem.get();

		ResItemDto resItemDto = new ResItemDto();
		resItemDto.setId(item.getId());
		resItemDto.setName(item.getName());
		resItemDto.setPrice(item.getPrice());
		resItemDto.setDesciption(item.getDesciption());
		resItemDto.setCategory_id(item.getCategory().getId());
		resItemDto.setImagePath(item.getImagePath());

		return resItemDto;
	}

	public String addNewItem(ReqItemDto reqItemDto) {
		Item getItem = itemRepository.getByName(reqItemDto.getName());
		if (getItem != null) {
			return "item already present";
		}
		Item item = new Item();
		item.setName(reqItemDto.getName());

		Optional<Category> c = categoryRepository.findById(reqItemDto.getCategory_id());
		Category category = c.get();

		item.setCategory(category);
		item.setDesciption(reqItemDto.getDesciption());
		item.setPrice(reqItemDto.getPrice());
		item.setImagePath(reqItemDto.getImagePath());
		itemRepository.save(item);
		return "new Item added !!";

	}

	public String updateSpecificItem(Long itemId, ReqItemDto reqItemDto) {
		Optional<Item> optionalItem = itemRepository.findById(itemId);

		if (!optionalItem.isPresent()) {
			new Exception("Item not found for id: " + itemId);
		}
		Item item = optionalItem.get();

		item.setName(reqItemDto.getName());
		item.setPrice(reqItemDto.getPrice());
		item.setDesciption(reqItemDto.getDesciption());
		Optional<Category> c = categoryRepository.findById(itemId);
		Category category = c.get();

		item.setCategory(category);
		item.setImagePath(item.getImagePath());
		itemRepository.save(item);
		return "Item updated!!";

	}

	public String deleteSpecificItem(Long itemId) {
		Optional<Item> optionalItem = itemRepository.findById(itemId);

		if (!optionalItem.isPresent()) {
			new Exception("Item not found for id: " + itemId);
		}
		orderRepository.modifyStatus(itemId, OrderStatus.CANCELLED);
		itemRepository.deleteById(itemId);

		return "item deleted";
	}
	
	/*
	 * methods for Order endpoints 
	 */

	public String addNewOrders(OrderReqDto orderReqDto) {
		Boolean checkOrder = orderRepository.existsByItemId(orderReqDto.getItem_id());
		int itemCount = orderReqDto.getItemCount() == 1 ? 1 : orderReqDto.getItemCount();

		Optional<Item> i = itemRepository.findById(orderReqDto.getItem_id());
		if (!i.isPresent()) {
			return "Item not found!";
		}
		BigDecimal totalprice = getTotalPrice(orderReqDto.getItem_id(), i.get().getPrice());
		if (checkOrder) {
			// increment item_count to 1 for each entry
			// issue in price addition when same item added (like same product added but
			// price still stay same)
			orderRepository.incrementCount(orderReqDto.getItem_id(), itemCount, totalprice);
			return "Item added to cart!!";
		}
		Order order = new Order();
		order.setItem(i.get());
		order.setOrderNumber(generateOrderNumber());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setShippingAddress(orderReqDto.getShippingAddress());
		order.setItemCount(itemCount);

		order.setTotalPrice(totalprice);
		order.setPaymentStatus(PaymentStatus.PENDING);
		orderRepository.save(order);
		return "Item added to cart!!";

	}

	private BigDecimal getTotalPrice(Long itemId, BigDecimal curItemPrice) {
		BigDecimal totalPrice = curItemPrice;
		List<BigDecimal> prices = itemRepository.getAllItemPrices(itemId);
		for (BigDecimal price : prices) {
			totalPrice = totalPrice.add(price);
		}
		return totalPrice;
	}

	public static Long generateOrderNumber() {
		Random random = new Random();
		long orderNumber = 1000000000L + (long) (random.nextDouble() * 9000000000L);
		return orderNumber;
	}

	public List<OrderResDto> getAllOrders() {
	    List<Order> orderList = orderRepository.findAll();
	    return orderList.stream()
	        .map(order -> new OrderResDto(
	                order.getId(),
	                order.getOrderNumber(),
	                order.getOrderStatus(),
	                order.getPaymentStatus(),
	                order.getTotalPrice(),
	                order.getShippingAddress(),
	                order.getItemCount()
	        ))
	        .collect(Collectors.toList()); 
	}
	
	public OrderResDto getSpecificOrder(Long orderId) {
		Optional<Order> getOrder = orderRepository.findById(orderId);
		if (!getOrder.isPresent()) {
			new Exception("Order not found for id: " + orderId);
		}
		Order order = getOrder.get();

		return new OrderResDto(
	            order.getId(),
	            order.getOrderNumber(),
	            order.getOrderStatus(),
	            order.getPaymentStatus(),
	            order.getTotalPrice(),
	            order.getShippingAddress(),
	            order.getItemCount()
	    );

	}

	public String updateSpecificOrders(Long orderId, OrderReqDto orderReqDto) {
	    Optional<Order> optionalOrder = orderRepository.findById(orderId);
	    if (!optionalOrder.isPresent()) {
	        new Exception("Order not found for id: " + orderId);
	    }
	    
	    Order order = optionalOrder.get();
	    
	    order.setShippingAddress(orderReqDto.getShippingAddress());
	    order.setItemCount(orderReqDto.getItemCount());
	    
	    orderRepository.save(order);
	    return "Order updated successfully"; 
	}


	public String deleteSpecificOrders(Long orderId) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
	    if (!optionalOrder.isPresent()) {
	        new Exception("Order not found for id: " + orderId);
	    }
	    
	    orderRepository.deleteById(orderId);
//	    orderRepository.updateOrderStatus(orderId, OrderStatus.CANCELLED);
	    return "Order deleted !!";

	}

}
