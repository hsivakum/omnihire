package com.example.demo.controller;

import com.example.demo.ResourceNotFoundException;
import com.example.demo.model.Stocks;
import com.example.demo.respository.StockRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/stock")
public class StocksController {

	@Autowired
	StockRepository stockRepository;
	
	@GetMapping("/allstocks")
	public List<Stocks> getAllStocks() {
	    return stockRepository.findAll();
	}
	
	@PostMapping("/addstocks")
	public Stocks createStock(@Valid @RequestBody Stocks stocks) {
	    return stockRepository.save(stocks);
	}
	
	@GetMapping("/getstocks/{id}")
	public Stocks getStocksById(@PathVariable(value = "id") Long stockId) {
	    return stockRepository.findById(stockId)
	            .orElseThrow(() -> new ResourceNotFoundException("Stock", "id", stockId));
	}
	
	@PutMapping("/updatestock/{id}")
	public Stocks updateStockById(@PathVariable(value = "id") Long stockId,
	                                        @Valid @RequestBody Stocks stocksDetails) {

	    Stocks stock = stockRepository.findById(stockId)
	            .orElseThrow(() -> new ResourceNotFoundException("Stock", "id", stockId));

	    stock.setitemname(stocksDetails.getitemname());
	    stock.setprice(stocksDetails.getprice());
	    stock.setinstock(stocksDetails.getinstock());
	    
	    Stocks updateStocks = stockRepository.save(stock);
	    return updateStocks;
	}
	
	@DeleteMapping("/deletestock/{id}")
	public ResponseEntity<?> deleteStock(@PathVariable(value = "id") Long stockId) {
	    Stocks stock = stockRepository.findById(stockId)
	            .orElseThrow(() -> new ResourceNotFoundException("Stocks", "id", stockId));

	    stockRepository.delete(stock);

	    return ResponseEntity.ok().build();
	}

	
	
}
