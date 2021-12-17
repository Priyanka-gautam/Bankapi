package com.example.Bankingapi.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bankingapi.entity.User;
import com.example.Bankingapi.exception.ResourseNotFoundException;
import com.example.Bankingapi.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class BankController {
	@Autowired
	private UserRepository userRepository;

	// get all users
	@GetMapping
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	// get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable (value = "id") long userId) {
		return this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourseNotFoundException("User not found with id :" + userId));
	}

	// create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}
	
	
	
	
	
}
