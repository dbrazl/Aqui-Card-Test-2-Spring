package com.aquicard.src.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.aquicard.src.Entity.User;
import com.aquicard.src.Repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository _userRepository;
	
	@GetMapping("/user")
	public ResponseEntity<List<User>> Get() {
		return new ResponseEntity<List<User>>(_userRepository.findAllAndSort(), HttpStatus.OK);
	}
	
	@GetMapping("/user/getbycode")
	public ResponseEntity<User> GetByCode (@RequestParam("code") long code) {
		
		Optional<User> user = _userRepository.findById(code);
		
		if(user.isPresent()) {
			return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/user/getbyname")
	public ResponseEntity<List<User>> GetByName (@RequestParam("name") String name) {
		
		List<User> users = _userRepository.findAllHasName(name);
		
		if(!users.isEmpty()) {
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> Post (@Valid @RequestBody User user) {
		try {
			return new ResponseEntity<User>(_userRepository.save(user), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/user")
	public ResponseEntity<User> Put(@RequestParam("code") long code, @Valid @RequestBody User user) {
		Optional<User> oldData= _userRepository.findById(code);
		
		if(oldData.isPresent()) {
			User newData= oldData.get();
			
			newData.setName(user.getName());
			newData.setBirthday(user.getBirthday());
			
			try {
				_userRepository.save(newData);
				return new ResponseEntity<User>(newData, HttpStatus.OK);
			} catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/user")
	public ResponseEntity<Object> Delete(@RequestParam("code") long code) {
		Optional<User> user = _userRepository.findById(code);
		
		if(user.isPresent()) {
			_userRepository.delete(user.get());
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
