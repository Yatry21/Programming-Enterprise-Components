package com.bank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "branch")
public class Branch extends Generic {

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "IBAN")
	private String iban;

	@Column(name = "address")
	private String address;

	public Branch() {
	}

	public Branch(String code, String name, String iban, String address) {
		super();
		this.code = code;
		this.name = name;
		this.iban = iban;
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
