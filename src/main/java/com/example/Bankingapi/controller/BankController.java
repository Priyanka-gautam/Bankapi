package com.example.Bankingapi.controller;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bankingapi.entity.AddAccountno;
import com.example.Bankingapi.entity.AddAmount;
import com.example.Bankingapi.entity.Transaction;
import com.example.Bankingapi.entity.User;
import com.example.Bankingapi.exception.ResourseNotFoundException;
import com.example.Bankingapi.repository.AddAccountnoRepository;
import com.example.Bankingapi.repository.AddAmountRepository;
import com.example.Bankingapi.repository.AddAmountUpdateRepository;
import com.example.Bankingapi.repository.FindAcoountRepository;
import com.example.Bankingapi.repository.TransactionRepository;
import com.example.Bankingapi.repository.UserRepository;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api")
public class BankController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddAmountRepository addAmountRepository;
	
	@Autowired
	private AddAmountUpdateRepository addAmountUpdateRepository;
	
	@Autowired
	private TransactionRepository transactionsRepository ;
	
		@Autowired
	private FindAcoountRepository findaccountno ;
	
		@Autowired
		private AddAccountnoRepository addaccountnoRepository;
		
	
		
	// get all users
	@GetMapping
	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

	// get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable (value = "id") long userId) {
		return this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourseNotFoundException("User not found with id :" + userId));
	}

	// create user
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}
	
	
	// create useraccount
		@PostMapping("/addamount")
		public AddAmount createAccount(@RequestBody AddAmount addamount) {
			return this.addAmountRepository.save(addamount);
		}
		
		// create update and deposite
				@PutMapping("/deposite")
				public AddAmount createdeposite(@RequestBody AddAmount addamount) {
					addAmountUpdateRepository.updatebalance(addamount.getBalance(),addamount.getAccountno());
					int checkaccno=0;
				try {	
				 checkaccno=findaccountno.getaccountnumber(addamount.getAccountno()); //check accountno is there if there it show that no, otherwise it show null
					System.out.println(checkaccno);			
				}
				catch(Exception e){
					System.out.println("Error account number not match"+e.getMessage());
					
				}
				//add into transaction
				int sentaccountno= addamount.getAccountno(); //this is for store json accountno 
				if(checkaccno==sentaccountno) {
				Transaction objecttransaction=new Transaction();
				objecttransaction.setSenderaccountno(addamount.getAccountno());
				objecttransaction.setRecieveraccountno(addamount.getAccountno());
				objecttransaction.setAmount(addamount.getBalance());
				Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				String Time = timestamp.toString();
				objecttransaction.setTime(Time);
				objecttransaction.setStatus("creadited");
				transactionsRepository.save(objecttransaction);
				System.out.println("Account number matched");
				}
				else {
					System.out.println("Account number not match");
				}
				return addamount;
				}
			
//				 create useraccountno
				@PostMapping("/addaccountno")
				public AddAccountno createAccountno(@RequestBody AddAccountno addaccountno) {
					return this.addaccountnoRepository.save(addaccountno);
				}
				
				@PutMapping("/transferamount")
				public String transfer(@RequestBody Transaction transfer) {
					
					//Kafka Producer
					
					Properties properties = new Properties();

		    		  // kafka bootstrap server
		    		  properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		    		  properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		    		  properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		    		  // producer acks
		    		  properties.setProperty(ProducerConfig.ACKS_CONFIG, "all"); // strongest producing guarantee
		    		  properties.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
		    		  properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "1");
		    		  // leverage idempotent producer from Kafka 0.11 !
		    		  properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true"); // ensure we don't push duplicates

		    		  Producer<String, String> producer = new KafkaProducer<>(properties);
		    		 
		    		  
					String sender=String.valueOf(transfer.getSenderaccountno());
					String receiver=String.valueOf(transfer.getRecieveraccountno());
					String amounts=String.valueOf(transfer.getAmount());
					
		    		  try {
	    		          producer.send(bankTransaction(sender,receiver,amounts));
	    		          Thread.sleep(100);


	    		      } catch (InterruptedException e) {

	    		      }

	    		  producer.close();

					return "checking";
				}
				
				
				public static ProducerRecord<String, String> bankTransaction(String accno,String amount,String raccno) {
			        // creates an empty json {}
			        ObjectNode transaction = JsonNodeFactory.instance.objectNode();			      
			        // Instant.now() is to get the current time using Java 8
			        Instant now = Instant.now();

			        // we write the data to the json document
			        transaction.put("SenderAccountnumber", accno);
			        transaction.put("ReceiverAccountnumber", raccno);
			        transaction.put("amount", amount);
			        transaction.put("time", now.toString());
			        return new ProducerRecord<>("bank1", accno, transaction.toString());
			    }
				
				
				
}
