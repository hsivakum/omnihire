package com.example.demo.model;


import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "cart")
@EntityListeners(AuditingEntityListener.class)
public class Cart  implements Serializable{
	
	public Cart()
	{
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    public Long id;
	
	@ManyToOne(fetch=FetchType.LAZY )
    @JoinColumn(name = "item_id", nullable=false,referencedColumnName="id")
	@JsonIgnore
    private Stocks item;
	
	@ManyToOne(fetch=FetchType.LAZY )
    @JoinColumn(name = "user_id",nullable=false, referencedColumnName="id")
	@JsonIgnore
    private User user;

    
	@NotNull
	private Integer quantity;
	
	
	public void setquantity(Integer quantity)
	{
		this.quantity = quantity;
	}
	
	public Integer getquantity()
	{
		return quantity;
	}
	
	public void setuser(User user)
	{
		this.user = user;
	}
	
	public User getuser()
	{
		return user;
	}
	
	public void setitem(Stocks item)
	{
		this.item= item;
	}
	
	
	public Stocks getitem()
	{
		return item;
	}

	
}
