package com.example.demo.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Stocks;

public interface StockRepository extends JpaRepository<Stocks, Long> {

}
