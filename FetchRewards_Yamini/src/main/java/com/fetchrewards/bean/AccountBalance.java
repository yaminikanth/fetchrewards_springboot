package com.fetchrewards.bean;

public class AccountBalance {

	String payerName;
	int balance;

	public void name() {

	}

	public AccountBalance(String payerName, int balance) {
		super();
		this.payerName = payerName;
		this.balance = balance;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

}
