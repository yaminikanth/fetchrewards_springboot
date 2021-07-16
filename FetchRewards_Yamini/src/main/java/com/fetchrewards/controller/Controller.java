package com.fetchrewards.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetchrewards.bean.AccountBalance;
import com.fetchrewards.bean.BalanceDeducted;
import com.fetchrewards.bean.RewardPoints;
import com.fetchrewards.bean.TransactionDetails;
import com.fetchrewards.service.ServiceImpl;

@RestController
@RequestMapping("/v1")
public class Controller {

	@Autowired
	private ServiceImpl service;

	@PostMapping(value = "/addBalance")
	public ResponseEntity<String> addBalance(@RequestBody TransactionDetails transaction) {
		return service.addBalance(transaction);
	}

	@PostMapping(value = "/spendRewardPoints")
	public ResponseEntity<List<BalanceDeducted>> spendRewardPoints(@RequestBody RewardPoints points) {

		return service.spendRewardPoints(points.getPoints());
	}

	@GetMapping(value = "/payersBalance")
	public ResponseEntity<List<AccountBalance>> payersBalance() {
		return service.payersBalance();
	}

}
