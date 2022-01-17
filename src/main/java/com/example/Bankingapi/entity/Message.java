package com.example.Bankingapi.entity;

public class Message {

	public String message = "message";
	public String status = "status";



	public String errorstatus = "404";
	public String successstatus = "202";
	// 202
	
	public String useraccount = "New user hase been created succesfully";
	public String deposite = "Amount has been creadited";
	public String transaction = "transaction is on the process, You will get a mail after the transaction is complete";
	public String addacount = "account added successfully ";
	public String emailreport = "Your transaction is successful, and your account balance is Rs. ";
	// 404
	public String accountnumbernotexist = "Account number doesn't exist";
	public String transactioninteruptreport = "Transaction interupted";
	public String accountadded = "Account added succesfully";
	public String mailalreadyexist = "Email id Or Phonenumber already exist";
	public String checkaccountnumber = "Please check accountnumber";



	public String credited = "credited";
	public String valid = "valid";
	public String testingtransaction = "Testing Transaction";
	
	
	public String getMessage() {
		return message;
	}
	public String getStatus() {
		return status;
	}
	public String getErrorstatus() {
		return errorstatus;
	}
	public String getSuccessstatus() {
		return successstatus;
	}
	public String getUseraccount() {
		return useraccount;
	}
	public String getDeposite() {
		return deposite;
	}
	public String getTransaction() {
		return transaction;
	}
	public String getAddacount() {
		return addacount;
	}
	public String getEmailreport() {
		return emailreport;
	}
	public String getAccountnumbernotexist() {
		return accountnumbernotexist;
	}
	public String getTransactioninteruptreport() {
		return transactioninteruptreport;
	}
	public String getAccountadded() {
		return accountadded;
	}
	public String getMailalreadyexist() {
		return mailalreadyexist;
	}
	public String getCheckaccountnumber() {
		return checkaccountnumber;
	}
	public String getCredited() {
		return credited;
	}
	public String getValid() {
		return valid;
	}
	public String getTestingtransaction() {
		return testingtransaction;
	}
	
	
}