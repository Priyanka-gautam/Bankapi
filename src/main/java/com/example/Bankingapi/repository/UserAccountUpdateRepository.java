package com.example.Bankingapi.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.Bankingapi.entity.UserAccount;

public interface UserAccountUpdateRepository extends JpaRepository <UserAccount, Long>{

	@Transactional
	@Modifying
	@Query("UPDATE UserAccount SET balance = balance + :balance WHERE accountno = :accountno")
	Integer updatebalance(int balance, int accountno);
	
	
}
