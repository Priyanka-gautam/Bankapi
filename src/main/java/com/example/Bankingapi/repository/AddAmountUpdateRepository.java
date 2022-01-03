package com.example.Bankingapi.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Bankingapi.entity.AddAmount;

@Repository
public interface AddAmountUpdateRepository extends JpaRepository <AddAmount, Long>{

	@Transactional
	@Modifying
	@Query("UPDATE AddAmount SET balance = balance + :balance WHERE accountno = :accountno")
	Integer updatebalance(int balance, int accountno);
	
}
