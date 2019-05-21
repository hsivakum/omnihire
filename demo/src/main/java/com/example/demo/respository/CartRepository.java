package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
	List<Cart> findByUserId(Long user_id);  
	List<Cart> findByItemId(Long item_id);  
}
