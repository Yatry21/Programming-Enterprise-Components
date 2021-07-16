package com.bank.entity;

import java.util.Date;

public class ResultDTO {

	private Date date;
	private Double deposit;
	private Double withdraw;
	private Double balance;
	private String accountName;
	private String accountNumber;
	private String currency;
	
	public ResultDTO(Date date, Double deposit, Double withdraw, Double balance, String accountName,
			String accountNumber, String currency) {
		super();
		this.date = date;
		this.deposit = deposit;
		this.withdraw = withdraw;
		this.balance = balance;
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.currency = currency;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public Double getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(Double withdraw) {
		this.withdraw = withdraw;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
