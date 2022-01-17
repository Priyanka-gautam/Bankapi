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

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
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
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api")
public class BankController {
	@Autowired
	private UserRepository userRepository;

	
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private UserAccountUpdateRepository userAccountUpdateRepository;
	
	@Autowired
	private TransactionRepository transactionsRepository ;
	
		@Autowired
	private FindUserAccountRepository findaccountno ;
	
		@Autowired
		private AddAccountnoRepository addaccountnoRepository;
		
	UserAccount useraccount =new UserAccount();
	Message allreports=new Message();
		
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
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		Map<String, String> map = new HashMap<String, String>();
		//user id
		Random random = new Random();
		int x = random.nextInt(899)+100;

		//acc number
		Integer y = random.nextInt(8999)+1000;
     int mid=0;
     
     try {

		 mid=userRepository.maxidnumber();
		 
    	 
     }
     catch(Exception e)
     {
    	 
     }
     
		try {
			
			String usernewid =String.valueOf(mid+1)+String.valueOf(x);
			String accountno=String.valueOf(mid+1)+String.valueOf(y);
			user.setUserid(Integer.parseInt(usernewid));
			user.setName(user.getName());
			user.setAccounttype(user.getAccounttype());
			user.setAddress(user.getAddress());
			user.setAge(user.getAge());
			user.setEmail(user.getEmail()); user.setPhonenumber(user.getPhonenumber());
			UserAccount deposit=new UserAccount();
			deposit.setAccountno(Integer.parseInt(accountno));
			deposit.setUserid(Integer.parseInt(usernewid));
			deposit.setBalance(0);
			
			userAccountRepository.save(deposit);

			map.put(allreports.getMessage(), allreports.getUseraccount());
			map.put(allreports.getStatus(), allreports.getSuccessstatus());
			userRepository.save(user);
		} 
		catch(Exception e)
		{
			map.put(allreports.getMessage(), allreports.mailalreadyexist);
			map.put(allreports.getStatus(), allreports.getErrorstatus());
		}
		return new ResponseEntity<Object>(map, HttpStatus.CREATED); 
	}
	
	
	// create useraccount
		@PostMapping("/useraccount")
		public  ResponseEntity<Object> createUserAccount(@Valid @RequestBody UserAccount useraccount) {
			Map<String, String> map = new HashMap<String, String>();
			try {
				map.put(allreports.getMessage(), allreports.getAccountadded());
				map.put(allreports.getStatus(), allreports.getSuccessstatus());
				userAccountRepository.save(useraccount);
				
			}
			catch(Exception e) {
				map.put(allreports.getMessage(), allreports.accountnumbernotexist);
				map.put(allreports.getStatus(), allreports.getErrorstatus());
			}
			return new ResponseEntity<Object>(map, HttpStatus.CREATED);
		}
		
		// create update and deposite
				@PutMapping("/deposite")
				public  ResponseEntity<Object> createdeposite(@RequestBody UserAccount useraccount) { //change to ResponseEntity json
				userAccountUpdateRepository. updatebalance(useraccount.getBalance(), useraccount.getAccountno());
					Map<String, String> map = new HashMap<String, String>();
					int checkaccno=0;
				
				try {
				
				//add into transaction
				
				checkaccno=findaccountno.getaccountnumber(useraccount.getAccountno());

				int sentaccountno= useraccount.getAccountno(); //this is for store json accountno 
				if(checkaccno==sentaccountno) {
				Transaction objecttransaction=new Transaction();
				objecttransaction.setSenderaccountno(useraccount.getAccountno());
				objecttransaction.setRecieveraccountno(useraccount.getAccountno());
				objecttransaction.setAmount(useraccount.getBalance());
				Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				String Time = timestamp.toString();
				objecttransaction.setTime(Time);
				objecttransaction.setStatus(allreports.getCredited());
				transactionsRepository.save(objecttransaction);
				map.put(allreports.getMessage(), allreports.getDeposite());
				map.put(allreports.getStatus(), allreports.getSuccessstatus());
				
				
				}
				else {
					//else condition
					return new ResponseEntity<Object>(map, HttpStatus.CREATED); 
					
				}
			}
				catch (Exception e)
				{
					}
				return new ResponseEntity<Object>(map, HttpStatus.CREATED); 
				
				}
		
				
//				 create add other accounts for sending
				@PostMapping("/addaccountno")
				public  ResponseEntity<Object> createAddAmount(@Valid @RequestBody AddAccountno addaccountno) {
					Map<String, String> map = new HashMap<String, String>();
					try {
						map.put(allreports.getMessage(), allreports.getAddacount());
						map.put(allreports.getStatus(), allreports.getSuccessstatus());
						 addaccountnoRepository.save(addaccountno);
						
					}
					catch(Exception e) {
						map.put(allreports.getMessage(), allreports.getCheckaccountnumber());
						map.put(allreports.getStatus(), allreports.getErrorstatus());
					}
					return new ResponseEntity<Object>(map, HttpStatus.CREATED);
				}
				
				
				
				@PutMapping("/transferamount")
				public ResponseEntity<Object> transfer(@RequestBody Transaction transfer) {
					
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
					Map<String, String> map = new HashMap<String, String>();
		    		  try {
		    			  
	    		          producer.send(bankTransaction(sender,amounts,receiver));
	    		          Thread.sleep(100);
	    		          map.put(allreports.getMessage(), allreports.getTransaction());
	    					map.put(allreports.getStatus(), allreports.getSuccessstatus());


	    		      } catch (InterruptedException e) {
	    		    	  map.put(allreports.getMessage(), allreports.transactioninteruptreport);
							map.put(allreports.getStatus(), allreports.getErrorstatus());
	    		      }

	    		  producer.close();
	    		  return new ResponseEntity<Object>(map, HttpStatus.CREATED);
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
				
				 @KafkaListener(topics = "bank4")
				    public void consumer(String message) throws IOException {
				    	
				    	JSONObject json = new JSONObject(message);
				        System.out.println(json.get("SenderAccountnumberb").toString());
				        
				        
				        String reportfromtransaction = json.get("Reportb").toString();
				        
				        String validss =reportfromtransaction;
				        
				        String saccountno = json.get("SenderAccountnumberb").toString();

				        int sendereccno=Integer.parseInt(saccountno);

				        
				        String raccountno = json.get("ReceiverAccountnumber").toString();
				        

				        int recivereccno=Integer.parseInt(raccountno);
				        
				     
				      
				        String amounttranfer = json.get("Amountbb").toString();
				        
				        int amounttranferint = Integer.parseInt(amounttranfer);
				       
				        	
				        System.out.println(reportfromtransaction);	
				        
				        String newreport=null;
				        int newbalance=0;
				        try {
				        	 Thread.sleep(3000);
				        	 newbalance=findaccountno.getuserbalance(sendereccno);
				         newreport =allreports.getEmailreport()+newbalance+"Transfered amount is :"+amounttranfer;
				        }
				        catch(Exception e)
				        {}
				       if(reportfromtransaction.equals("Transfered"))

				        {
				        try {
				       
				        //sendEmailWithAttachment();
				        	 sendEmail(newreport,"priyanka011g@gmail.com");


				        } catch (MessagingException e) {
				        e.printStackTrace();
				        }



				        System.out.println("Done");

				        }

				        }
				        
				        				        
				 public void sendEmail(String rep,String mailid) {

			        	SimpleMailMessage msg = new SimpleMailMessage();
			        	msg.setTo(mailid);



			        	msg.setSubject("Testing Transaction ");
			        	msg.setText(rep);



			        	javaMailSender.send(msg);


			        	}
	
				
}
