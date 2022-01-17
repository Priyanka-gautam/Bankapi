package com.example.Bankingapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="addaccounts")
public class AddAccountno {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
    @NotNull
	@Column(name ="name")
	private String name;
    
    @NotNull
  	@Column(name = "recieveraccountno",unique=true)
  	private int recieveraccountno;
    
    @NotNull
	@Column(name = "bankname")
	private String bankname;

    @NotNull
	@Column(name = "ifccode")
	private String ifccode;

	public AddAccountno(@NotNull String name, @NotNull int recieveraccountno, @NotNull String bankname,
			@NotNull String ifccode) {
		super();
		this.name = name;
		this.recieveraccountno = recieveraccountno;
		this.bankname = bankname;
		this.ifccode = ifccode;
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

	public int getRecieveraccountno() {
		return recieveraccountno;
	}

	public void setRecieveraccountno(int recieveraccountno) {
		this.recieveraccountno = recieveraccountno;
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