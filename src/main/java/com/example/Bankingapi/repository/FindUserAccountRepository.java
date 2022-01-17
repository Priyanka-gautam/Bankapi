package com.example.Bankingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Bankingapi.entity.UserAccount;

public interface FindUserAccountRepository extends JpaRepository <UserAccount, Long>{

	
	@Query(value="select accountno from useraccount u where u.accountno =:Anumber", nativeQuery=true)
	Integer getaccountnumber(@Param("Anumber") int Anumber);
	
	@Query(value="select balance from useraccount u where u.accountno =:Bnumber", nativeQuery=true)
	Integer getuserbalance(@Param("Bnumber") int Bnumber);
}
