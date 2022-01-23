package com.example.Bankingapi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.Bankingapi.entity.AddAccountno;
import com.example.Bankingapi.entity.Message;
import com.example.Bankingapi.entity.User;
import com.example.Bankingapi.entity.UserAccount;
import com.example.Bankingapi.repository.AddAccountnoRepository;
import com.example.Bankingapi.repository.FindUserAccountRepository;
import com.example.Bankingapi.repository.UserAccountRepository;
import com.example.Bankingapi.repository.UserRepository;

@Service
@Transactional
public class BankService {

	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private AddAccountnoRepository addaccountnoRepository;
	
	@Autowired
	private FindUserAccountRepository emailphone;
	
	
	UserAccount useraccount = new UserAccount();
	Message allreports = new Message();
	//User create
	public Object UserService(User user) {
		Map<String, String> map = new HashMap<String, String>();
		// user id
		Random random = new Random();
		int x = random.nextInt(899) + 100;

		// acc number
		Integer y = random.nextInt(8999) + 1000;
		int mid = 0;
		String checkemail= null;
		String phonen=null;
		try {
			mid = userRepository.maxidnumber();  // here its showing null because table not have any value
			checkemail=emailphone.getemail(user.getEmail());
			phonen=emailphone.phone(user.getPhonenumber());
			
		} catch (Exception e) {
		
			mid = 0;
		}
		if(checkemail==null && phonen==null) {
		String usernewid = String.valueOf(mid + 1) + String.valueOf(x);
		String accountno = String.valueOf(mid + 1) + String.valueOf(y);
		user.setUserid(Integer.parseInt(usernewid));
		user.setName(user.getName());
		user.setAccounttype(user.getAccounttype());
		user.setAddress(user.getAddress());
		user.setAge(user.getAge());
		user.setEmail(user.getEmail());
		user.setPhonenumber(user.getPhonenumber());
		UserAccount deposit = new UserAccount();
		deposit.setAccountno(Integer.parseInt(accountno));
		deposit.setUserid(Integer.parseInt(usernewid));
		deposit.setBalance(0);
		userAccountRepository.save(deposit);
		map.put(allreports.getMessage(), allreports.getUseraccount());
		map.put(allreports.getStatus(), allreports.getSuccessstatus());
		userRepository.save(user);
		}
		else {
	map.put(allreports.getMessage(), allreports.mailalreadyexist);
		map.put(allreports.getStatus(), allreports.getErrorstatus());
		}
		
		return map;
		
	}
	
	//Addaccount Service
	public Object AddAccountService(AddAccountno accountno) {
		int reciveraccno=0;
		Map<String, String> map = new HashMap<String, String>();
		try {
			reciveraccno=addaccountnoRepository.receiveraccno(accountno.getRecieveraccountno());
		}
		catch (Exception e)
		{
			
		}
		if (reciveraccno==0) {

		map.put(allreports.getMessage(), allreports.getAddacount());
		map.put(allreports.getStatus(), allreports.getSuccessstatus());
		addaccountnoRepository.save(accountno);
		}
		else {	map.put(allreports.getMessage(), allreports.getCheckaccountnumber());
			map.put(allreports.getStatus(), allreports.getErrorstatus());
		}
		return map;
		
	}
}
