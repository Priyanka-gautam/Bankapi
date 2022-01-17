package com.example.Bankingapi.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotEmpty
	@Column(name = "name" ,length = 20)
	private String name;
	
	@NotNull
	@Column(name = "userid" ,length = 20)
	private int userid;
	
	@NotEmpty
	@Column(name = "address",length = 40)
	private String address;
	
	@NotEmpty
	@Email
	@Column(name = "email" ,length = 40,unique=true)
	private String email;
	
	@NotNull
	@Column(name = "age",length = 40)
	private int age;
	
	@NotEmpty
	@Size(min = 10,max=10,message = "phonenumber should have  10 characters")   
	@Column(name = "phonenumber",length = 40,unique=true)
	private String phonenumber;
	
	@NotEmpty
	@Column(name = "accounttype",length = 40)
	private String accounttype;



	public User(String name, int userid, String address, String email, int age, String phonenumber,
			String accounttype) {
		super();
		this.name = name;
		this.userid = userid;
		this.address = address;
		this.email = email;
		this.age = age;
		this.phonenumber = phonenumber;
		this.accounttype = accounttype;
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

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	
	
}

	