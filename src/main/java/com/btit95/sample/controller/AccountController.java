package com.btit95.sample.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btit95.sample.entities.Account;
import com.btit95.sample.exception.ConflictException;
import com.btit95.sample.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("account-service")
@Slf4j
public class AccountController {
	private List<Account> accounts;

	public AccountController() {
		this.accounts = new ArrayList<>();
		this.accounts.add(new Account("admin", "admin"));
		this.accounts.add(new Account("baotoan", "baotoan123"));
		this.accounts.add(new Account("hoa.hcm", "12345"));
	}

	@GetMapping("/")
	public List<Account> findAll() {
		return this.accounts;
	}

	@GetMapping("/{username}")
	public Account findByUsername(@PathVariable("username") String username) {
		log.debug("Getting account's info with username is {}", username);
		try {
			Account account = this.accounts.stream().filter(acc -> acc.getUsername().equals(username)).findFirst()
					.get();
			return account;
		} catch (Exception e) {
			throw new NotFoundException("This account (" + username + ") does not exist in the system");
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Void> addNewAccount(@RequestBody Account account) {
		try {
			// Check account existed, this throw exception if not found
			this.accounts.stream().filter(ac -> ac.getUsername().equals(account.getUsername())).findFirst().get();
			throw new ConflictException("The username existed. Please choose a new one and try again.");
		} catch (Exception e) {
			// Account does not existed yet, so create a new one
			this.accounts.add(account);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<Void> updateAccount(@RequestBody Account account) {
		Account acc = this.accounts.stream().filter(ac -> ac.getUsername().equals(account.getUsername())).findFirst().get();
		acc.setPassword(account.getPassword());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
