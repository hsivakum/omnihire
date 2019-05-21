package com.example.demo.model;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@SuppressWarnings("serial")
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User  implements Serializable{
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Long id;
	
	@NotBlank
    private String name;

    @NotBlank
    private String password;
 
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Cart> cart;
    
    public User()
	{
		
	}
    
    public User(String name, String password) {    
        this.name = name;
        this.password = password;
    }
    
    public void setName(String names)
    {
    	this.name = names;
    }
    
    public String getName()
    {
    	return name;
    }
     
    public void setPassword(String pass)
    {
    	this.password = pass;
    }
    
    public String getPassword()
    {
    	return password;
    }
    
    public void setcart(Set<Cart> cart)
    {
    	this.cart = cart;
    }
    
    public Set<Cart> getcart()
    {
    	return cart;
    }
    
}
