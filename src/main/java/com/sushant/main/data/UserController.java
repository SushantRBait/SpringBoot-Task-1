package com.sushant.main.data;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
	@Autowired
	private UserServices services;

//	  @PostMapping(path="/register/user")
//	  public User registerUser() {
//	  System.out.println("In registerUser");
//	  User user1 = new User();           
//	  UserServices.getInstance().add(user1);
//	  
//	  
//	        //We are setting the below value just to reply a message back to the caller
//	         user1.setName(user1.getName());
//	         user1.setSurname(user1.getSurname());
//	         user1.setPincode(user1.getPincode());
//	         user1.setDob(user1.getDob());
//	         user1.setJoinDate(user1.getJoinDate());
//	         
//	        return user1;
//	}
	
	@PostMapping(path = "/register/user")
	public User registerUser(@RequestBody User user) {
		services.save(user);
		return user;
	}
	
	@GetMapping("/user/all")
	public List<User> list() {
		return services.listAll();
	}

	@GetMapping(path = "/user/id/{id}")
	public ResponseEntity<User> searchUserRecordByName(@PathVariable("id") Integer id) {

		try {
			User user = services.get(id);
			System.out.println("In searchUserRecordBy Id");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/user/name/{name}")
	public ResponseEntity<User> searchUserRecordByName(@PathVariable("name") String name) {
		try {
			User user = services.getUserRecordByName(name);
			System.out.println("In searchUserRecordBy Name");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/user/surname/{surname}")
	public ResponseEntity<User> searchUserRecordBySurname(@PathVariable("surname") String surname) {
		try {
			User user = services.getUserRecordBySurname(surname);
			System.out.println("In searchUserRecordBy Surname");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/user/pincode/{pincode}")
	public ResponseEntity<User> searchUserRecordBySurname(@PathVariable("pincode") Integer pincode) {
		try {
			User user = services.getUserRecordByPincode(pincode);
			System.out.println("In searchUserRecordBy Pincode");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}


	@PutMapping(path = "/update/user/{id}")
	public ResponseEntity<User> updateStudentRecord(@RequestBody User user, @PathVariable Integer id) {
		try {
			User existUser = services.get(id);
			services.save(existUser);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/user/{id}")
	public void deleteUser(@PathVariable("id") Integer id) {
		services.delete(id);
	}
	
	@GetMapping("/sort/asc")
	public List<User> getAllAsc (@RequestParam String field) {
		return services.findAll(Sort.by(Direction.ASC, field));
	}
	@GetMapping("/sort/desc")
	public List<User> getAllDesc (@RequestParam String field) {
		return services.findAll(Sort.by(Direction.DESC, field));
	}
}
