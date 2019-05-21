package com.example.demo.model;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "stocks")
@EntityListeners(AuditingEntityListener.class)
public class Stocks implements Serializable {
	
	public Stocks()
	{
		
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	private String itemname;
	
	@NotNull
	private Integer price;
	
	@NotNull
	private Integer instock;
	
	@OneToMany(mappedBy = "item")
    private Set<Cart> cart;
	
	public Set<Cart> getcart()
	{
		return cart;
	}
	public void setcart(Set<Cart> cart)
	{
		this.cart = cart;
	}
	
	public void setitemname(String itemname)
	{
		this.itemname = itemname;
	}
	
	public String getitemname()
	{
		return itemname;
	}
	
	public void setprice(Integer price)
	{
		this.price = price;
	}
	
	public Integer getprice()
	{
		return price;
	}
	
	public void setinstock(Integer stock)
	{
		this.instock = stock;
	}
	
	public Integer getinstock()
	{
		return instock;
	}
	
	public Long getid()
	{
		return id;
	}
}
