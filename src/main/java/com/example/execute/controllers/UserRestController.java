package com.example.execute.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.execute.binding.LoginForm;
import com.example.execute.binding.UserForm;
import com.example.execute.service.UserMgmtService;

@RestController
public class UserRestController {

	@Autowired
	private UserMgmtService service;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
		String status = service.login(loginForm);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@GetMapping("/countries")
	public Map<Integer, String> loadCountries() {
		return service.getCountries();
	}

	@GetMapping("/states/{countryId}")
	public Map<Integer, String> loadStates(@PathVariable Integer countryId) {
		return service.getStates(countryId);
	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> loadCities(@PathVariable Integer stateId) {
		return service.getCities(stateId);
	}

	@GetMapping("/email/{email}")
	public String checkEmail(@PathVariable String email) {
		return service.checkEmail(email);

	}

	@PostMapping("/user")
	public ResponseEntity<String> userRegisration(@RequestBody UserForm userForm) {
		String status = service.registerUser(userForm);
		return new ResponseEntity<>(status, HttpStatus.CREATED);
	}
       @GetMapping("/forgotPwd/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable String email) {
		String status = service.forgotPwd(email);
		return new ResponseEntity<>(status, HttpStatus.OK);

	}

	@PostMapping("/unlock")
	public ResponseEntity<String> unlockAccount(@RequestBody UnlockAccForm unlockAccForm) {
		String status = service.unlockAccount(unlockAccForm);
		return new ResponseEntity<>(status, HttpStatus.OK);

	}
}
