package com.example.demo.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dto.ResItemDto;
import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

	Item getByName(String Name);

	@Query("SELECT new com.example.demo.dto.ResItemDto(l.id, l.name, l.price, l.desciption, l.category.id, l.imagePath) FROM Item l")
	List<ResItemDto> findListOfItem();

	@Query("SELECT i.price FROM Item i where i.id = :itemId")
	List<BigDecimal> getAllItemPrices(@Param("itemId") Long itemId);



	



	
}
