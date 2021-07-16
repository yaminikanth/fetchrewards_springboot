package com.fetchrewards.bean;

import java.time.LocalDateTime;

public class BalanceDeducted extends AccountBalance {

	LocalDateTime time;

	public BalanceDeducted(String payerName, int balance, LocalDateTime time) {
		super(payerName, balance);
		this.time = time;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
