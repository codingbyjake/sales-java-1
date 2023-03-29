package com.maxtrain.PRS.User;

// Imports
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController { // start of class

	@Autowired
	private UserRepository userRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<User>> getUser(){
		Iterable<User> users = userRepo.findAll();
		return new ResponseEntity<Iterable<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable int id){
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<User> putUser(@PathVariable int id, @RequestBody User user){
		if(user.getId() != id) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		User updatedUser = userRepo.save(user);
		return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<User> postUser(@RequestBody User user){
		User newUser = userRepo.save(user);
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id){
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepo.delete(user.get());
		return new ResponseEntity<User>(user.get(), HttpStatus.GONE);
	}
	
	
	
} // end of class
