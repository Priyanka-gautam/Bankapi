package com.example.Bankingapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="addaccounts")
public class AddAccountno {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "senderaccountno",unique=true)
	private int senderaccountno;
	
	@Column(name = "name")
	private String name;
	
	
	@Column(name = "bankname")
	private String bankname;
	
	@Column(name = "ifccode")
	private String ifccode;

	public AddAccountno(int senderaccountno, String name, String bankname, String ifccode) {
		super();
		this.senderaccountno = senderaccountno;
		this.name = name;
		this.bankname = bankname;
		this.ifccode = ifccode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getSenderaccountno() {
		return senderaccountno;
	}

	public void setSenderaccountno(int senderaccountno) {
		this.senderaccountno = senderaccountno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getIfccode() {
		return ifccode;
	}

	public void setIfccode(String ifccode) {
		this.ifccode = ifccode;
	}

	
	
	
}
