package com.example.Bankingapi.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "age")
	private int age;
	
	@Column(name = "phonenumber")
	private String phonenumber;
	
	@Column(name = "accounttype")
	private String accountype;
	
	

	public User(String name, String address, String email, int age, String phonenumber, String accountype) {
		super();
		this.name = name;
		this.address = address;
		this.email = email;
		this.age = age;
		this.phonenumber = phonenumber;
		this.accountype = accountype;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAccountype() {
		return accountype;
	}

	public void setAccountype(String accountype) {
		this.accountype = accountype;
	}
	
	
	
	
}
