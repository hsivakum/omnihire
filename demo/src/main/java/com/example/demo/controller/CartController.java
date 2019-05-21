package com.example.demo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Conversion;
import com.example.demo.ResourceNotFoundException;
import com.example.demo.model.Cart;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.UserRepository;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/viewcart",method = RequestMethod.POST, consumes = MediaType
            .APPLICATION_FORM_URLENCODED_VALUE , produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public String getAllItemsByUserId(@RequestBody MultiValueMap<String, String> formParams) {
		 System.out.println("ID is " + formParams);
		 JSONArray jsonArray = null;
		 Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/data_store", "postgres", "1234");
            Statement statement = connection.createStatement();
            String s = "SELECT c.id,c.quantity,c.item_id,c.user_id, (s.price*c.quantity) as total,s.itemname from cart c inner join stocks s on \r\n" + 
            		"s.id = c.item_id where user_id = '"+formParams.getFirst("user_id")+"'";
            ResultSet resultSet = statement.executeQuery(s);
            jsonArray = Conversion.convert(resultSet);        
        } 
         catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	    return jsonArray.toString();
	}
	
	
	
	
	
	
	@RequestMapping(value="/additems",method = RequestMethod.POST, consumes = MediaType
            .APPLICATION_FORM_URLENCODED_VALUE , produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public String createcart(@RequestBody MultiValueMap<String, String> formParams) {
		 System.out.println("ID is " + formParams);
		 JSONArray jsonArray = null;
		 JSONObject jsonObject =  null;
		 Connection connection = null;
		 Map<String, String> map = new HashMap<String, String>();
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/data_store", "postgres", "1234");
            Statement statement = connection.createStatement();
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            Statement statement3 = connection.createStatement();
            String s1 = "SELECT * FROM users where id ='"+formParams.getFirst("user_id")+"'";
            ResultSet resultSet = statement.executeQuery(s1);
            String s2 = "Select * from stocks where id ='"+formParams.getFirst("item_id")+"'";
            ResultSet resultSet1 = statement1.executeQuery(s2);
            String s4 = "SELECT max(id) as id from cart";
            ResultSet resultSet3 = statement3.executeQuery(s4);
            int id=0;
            if(resultSet3.next())
            {
            	id = resultSet3.getInt("id")+1;
            }
            if(resultSet.next() && resultSet1.next())
            {
            if((resultSet.getInt("id")>0) && (resultSet1.getInt("id")>0))
            {
            	String s = "insert into cart (id,quantity,item_id,user_id)values("+id+","+formParams.getFirst("quantity")+","+formParams.getFirst("item_id")+","+formParams.getFirst("user_id")+")";
            	System.out.println(s);
            	int res = statement2.executeUpdate(s);
            	if(res==1)
            	{
            		map.put("user_id", formParams.getFirst("user_id"));
            		map.put("item_id",formParams.getFirst("item_id"));
            		map.put("quantity", formParams.getFirst("quantity"));
            	}
            }
            }
            System.out.println(map);
            jsonObject = new JSONObject(map);
            jsonArray = new JSONArray("["+jsonObject.toString()+"]");
            
        } 
         catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	    return jsonArray.toString();
	}

	@RequestMapping(value="/getparticular",method = RequestMethod.POST, consumes = MediaType
            .APPLICATION_FORM_URLENCODED_VALUE , produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public String getAllItemsById(@RequestBody MultiValueMap<String, String> formParams) {
		 System.out.println("ID is " + formParams);
		 JSONArray jsonArray = null;
		 Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/data_store", "postgres", "1234");
            Statement statement = connection.createStatement();
            String s = "SELECT c.id,c.quantity,c.item_id,c.user_id, (s.price*c.quantity) as total,s.itemname from cart c inner join stocks s on \r\n" + 
            		"s.id = c.item_id where user_id = '"+formParams.getFirst("user_id")+"' and c.item_id='"+formParams.getFirst("item_id")+"'";
            ResultSet resultSet = statement.executeQuery(s);
            jsonArray = Conversion.convert(resultSet);        
        } 
         catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	    return jsonArray.toString();
	}
	
	
	@PutMapping("/updateitem/{id}")
	public Cart updatecartById(@PathVariable(value = "id") Long cartId,
	                                        @Valid @RequestBody Cart cartDetails) {

	    Cart cart = cartRepository.findById(cartId)
	            .orElseThrow(() -> new ResourceNotFoundException("cart", "id", cartId));

	    cart.setquantity(cartDetails.getquantity());
	    cart.setitem(cartDetails.getitem());
	    cart.setuser(cartDetails.getuser());
	    
	    Cart updatecart = cartRepository.save(cart);
	    return updatecart;
	}
	
	@RequestMapping(value="/deleteitem",method = RequestMethod.POST, consumes = MediaType
            .APPLICATION_FORM_URLENCODED_VALUE , produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public String deleteCartByItemId(@RequestBody MultiValueMap<String, String> formParams) {
		 Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/data_store", "postgres", "1234");
            Statement statement = connection.createStatement();
            String s = "DELETE FROM cart where item_id = '"+formParams.getFirst("item_id")+"' and user_id = '"+formParams.getFirst("user_id")+"'";
            statement.executeUpdate(s);
               
        } 
         catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	    return null;
	}

	
	
}
