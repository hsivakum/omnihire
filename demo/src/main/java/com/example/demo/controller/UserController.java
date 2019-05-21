package com.example.demo.controller;

import com.example.demo.Conversion;
import com.example.demo.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.respository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.validation.Valid;
import java.util.List;

import org.json.JSONArray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
    UserRepository userRepository;
	
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
	    return userRepository.findAll();
	}
	
	@RequestMapping(value="/users/getparticular",method = RequestMethod.POST, consumes = MediaType
            .APPLICATION_FORM_URLENCODED_VALUE , produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public String getById(@RequestBody MultiValueMap<String, String> formParams) {
		 System.out.println("ID is " + formParams);
		 JSONArray jsonArray = null;
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/data_store", "postgres", "1234")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users where name ='"+formParams.getFirst("name")+"'");
            jsonArray = Conversion.convert(resultSet);
            
        } 
         catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
		
	    return jsonArray.toString();
	}
	
	
	
	@PostMapping("/users/create")
	public User createUser(@Valid @RequestBody User user) {
	    return userRepository.save(user);
	}
	
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable(value = "id") Long userId) {
	    return userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", userId));
	}
	
	@PutMapping("/users/udpdateuser/{id}")
	public User updateUser(@PathVariable(value = "id") Long userId,
	                                        @Valid @RequestBody User userDetails) {

	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", userId));

	    user.setName(userDetails.getName());
	    user.setPassword(userDetails.getPassword());

	    User updateUser = userRepository.save(user);
	    return updateUser;
	}
	
	@DeleteMapping("/users/deleteuser/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", userId));

	    userRepository.delete(user);

	    return ResponseEntity.ok().build();
	}

}
