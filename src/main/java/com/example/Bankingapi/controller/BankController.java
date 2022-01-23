package com.example.Bankingapi.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;


import javax.validation.Valid;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;


import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bankingapi.config.BankProducerConfig;
import com.example.Bankingapi.entity.AddAccountno;
import com.example.Bankingapi.entity.Message;
import com.example.Bankingapi.entity.Transaction;
import com.example.Bankingapi.entity.User;
import com.example.Bankingapi.entity.UserAccount;
import com.example.Bankingapi.exception.ResourseNotFoundException;
import com.example.Bankingapi.repository.AddAccountnoRepository;
import com.example.Bankingapi.repository.FindUserAccountRepository;
import com.example.Bankingapi.repository.TransactionRepository;
import com.example.Bankingapi.repository.UserAccountRepository;
import com.example.Bankingapi.repository.UserAccountUpdateRepository;
import com.example.Bankingapi.repository.UserRepository;
import com.example.Bankingapi.service.BankService;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api")
public class BankController {
	
	static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(BankController.class.getName());
	
	@Autowired
   private BankProducerConfig bankproducer;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private UserAccountUpdateRepository userAccountUpdateRepository;

	@Autowired
	private TransactionRepository transactionsRepository;

	@Autowired
	private FindUserAccountRepository findaccountno;

	@Autowired
	private BankService  service;

	UserAccount useraccount = new UserAccount();
	Message allreports = new Message();
    
	// get all users
	@GetMapping
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
		}
		
	/** get user by id - 
	Here this mapping used for find Users Account Balance with using users Account Number */
				@GetMapping("/{id}")
				public User getUserById(@PathVariable(value = "id") long userId) {
					return this.userRepository.findById(userId)
							.orElseThrow(() -> new ResourseNotFoundException("User not found with id :" + userId));
				
	}

	
	// create user
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
	    Object userrepo=service.UserService(user);
		return new ResponseEntity<Object>(userrepo, HttpStatus.CREATED);
	}

	// create useraccount
	@PostMapping("/useraccount")
	public ResponseEntity<Object> createUserAccount(@Valid @RequestBody UserAccount useraccount) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put(allreports.getMessage(), allreports.getAccountadded());
			map.put(allreports.getStatus(), allreports.getSuccessstatus());
			userAccountRepository.save(useraccount);

		} catch (Exception e) {
			map.put(allreports.getMessage(), allreports.accountnumbernotexist);
			map.put(allreports.getStatus(), allreports.getErrorstatus());
           logger.error(allreports.getMailalreadyexist());
		}
		return new ResponseEntity<Object>(map, HttpStatus.CREATED);
	}

	// create update and deposite
	@PutMapping("/deposite")
	public ResponseEntity<Object> createdeposite(@RequestBody UserAccount useraccount) { // change to ResponseEntity
																							// json
		userAccountUpdateRepository.updatebalance(useraccount.getBalance(), useraccount.getAccountno());
		Map<String, String> map = new HashMap<String, String>();
		int checkaccno = 0;

		try {
			// add into transaction
			checkaccno = findaccountno.getaccountnumber(useraccount.getAccountno());
			int sentaccountno = useraccount.getAccountno(); // this is for store json accountno
			if (checkaccno == sentaccountno) {
				Transaction objecttransaction = new Transaction();
				objecttransaction.setSenderaccountno(useraccount.getAccountno());
				objecttransaction.setRecieveraccountno(useraccount.getAccountno());
				objecttransaction.setAmount(useraccount.getBalance());
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				String Time = timestamp.toString();
				objecttransaction.setTime(Time);
				objecttransaction.setStatus(allreports.getCredited());
				transactionsRepository.save(objecttransaction);
				map.put(allreports.getMessage(), allreports.getDeposite());
				map.put(allreports.getStatus(), allreports.getSuccessstatus());
				
			} else {
				return new ResponseEntity<Object>(map, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			map.put(allreports.getMessage(), allreports.getCheckaccountnumber());
			map.put(allreports.getStatus(), allreports.getErrorstatus());
			logger.error(allreports.getCheckaccountnumber());
		}
		return new ResponseEntity<Object>(map, HttpStatus.CREATED);
	}

	@PostMapping("/addaccountno")
	public ResponseEntity<Object> createAddAmount(@Valid @RequestBody AddAccountno addaccountno) {
		
		Object message= service.AddAccountService(addaccountno);
		//try {
			
	//	} catch (Exception e) {
		//	map.put(allreports.getMessage(), allreports.getCheckaccountnumber());
		//	map.put(allreports.getStatus(), allreports.getErrorstatus());
		//	logger.error(allreports.getCheckaccountnumber());
	//	}
		return new ResponseEntity<Object>(message, HttpStatus.CREATED);
	}

	@PutMapping("/transferamount")
	public ResponseEntity<Object> transfer(@RequestBody Transaction transfer) {

		Producer<String, String> producer = new KafkaProducer<>(bankproducer.Bankproducer());
		String sender = String.valueOf(transfer.getSenderaccountno());
		String receiver = String.valueOf(transfer.getRecieveraccountno());
		String amounts = String.valueOf(transfer.getAmount());
		Map<String, String> map = new HashMap<String, String>();
		try {
			producer.send(bankTransaction(sender, amounts, receiver));
			Thread.sleep(100);
			map.put(allreports.getMessage(), allreports.getTransaction());
			map.put(allreports.getStatus(), allreports.getSuccessstatus());
		} catch (InterruptedException e) {
			map.put(allreports.getMessage(), allreports.transactioninteruptreport);
			map.put(allreports.getStatus(), allreports.getErrorstatus());
			logger.error(allreports.getTransactioninteruptreport());
		}
        producer.close();
		return new ResponseEntity<Object>(map, HttpStatus.CREATED);
	}

	public static ProducerRecord<String, String> bankTransaction(String accno, String amount, String raccno) {
		// creates an empty json {}
		ObjectNode transaction = JsonNodeFactory.instance.objectNode();
		// Instant.now() is to get the current time using Java 8
		Instant now = Instant.now();
		transaction.put("SenderAccountnumber", accno);
		transaction.put("ReceiverAccountnumber", raccno);
		transaction.put("amount", amount);
		transaction.put("time", now.toString());
		return new ProducerRecord<>("bank1", accno, transaction.toString());
	}

	@KafkaListener(topics = "bank4")
	public void consumer(String message) throws IOException {
		JSONObject json = new JSONObject(message);
		System.out.println(json.get("SenderAccountnumberb").toString());
		String reportfromtransaction = json.get("Reportb").toString();
		String validss = reportfromtransaction;
		String saccountno = json.get("SenderAccountnumberb").toString();
		int sendereccno = Integer.parseInt(saccountno);
		String raccountno = json.get("ReceiverAccountnumber").toString();
		int recivereccno = Integer.parseInt(raccountno);
		String amounttranfer = json.get("Amountbb").toString();
		int amounttranferint = Integer.parseInt(amounttranfer);
		System.out.println(reportfromtransaction);
		String newreport = null;
		int newbalance = 0;
		try {
			Thread.sleep(3000);
			newbalance = findaccountno.getuserbalance(sendereccno);
			newreport = allreports.getEmailreport() + newbalance + "Transfered amount is :" + amounttranfer;
			
		} catch (Exception e) {
		}
		if (reportfromtransaction.equals("Transfered"))

		{
			try {
				sendEmail(newreport, "priyanka011g@gmail.com");
				
			} catch (MessagingException e) {
				e.printStackTrace();  
			}
		
			logger.debug("done");
		}
	}
	public void sendEmail(String rep, String mailid) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mailid);
		msg.setSubject("Testing Transaction ");
		msg.setText(rep);
		javaMailSender.send(msg);
	}

	// Get balance
	@GetMapping("/checkbalance")
	public ResponseEntity<Object> BalanceCheck(@Valid @RequestBody UserAccount useraccount) {
		Map<String, String> map = new HashMap<String, String>();
		int newbalance = 0;
		try {
			newbalance = findaccountno.getuserbalance(useraccount.getAccountno());
			map.put(allreports.getMessage(), allreports.balances + newbalance);
			map.put(allreports.getStatus(), allreports.getSuccessstatus());

		} catch (Exception e) {
			map.put(allreports.getMessage(), allreports.checkaccountnumber);
			map.put(allreports.getStatus(), allreports.getErrorstatus());
		}
		return new ResponseEntity<Object>(map, HttpStatus.CREATED);
	}
}
