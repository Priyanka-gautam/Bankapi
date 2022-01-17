package com.example.Bankingapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "senderaccountno")
	private int senderaccountno;
	
	@Column(name = "recieveraccountno")
	private int recieveraccountno;
	
	@Column(name = "amount")
	private int amount;
	
	
	@Column(name = "time")
	private String time;
	
	@Column(name = "status")
	private String status;
	


	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
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



	public int getRecieveraccountno() {
		return recieveraccountno;
	}



	public void setRecieveraccountno(int recieveraccountno) {
		this.recieveraccountno = recieveraccountno;
	}



	public int getAmount() {
		return amount;
	}



	public void setAmount(int amount) {
		this.amount = amount;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}


	
}
