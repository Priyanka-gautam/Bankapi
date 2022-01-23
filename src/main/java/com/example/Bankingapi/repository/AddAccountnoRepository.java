package com.example.Bankingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Bankingapi.entity.AddAccountno;

public interface AddAccountnoRepository extends JpaRepository <AddAccountno, Long>{
	
	@Query(value="select recieveraccountno from addaccounts u where u.recieveraccountno =:Rrecieveraccountno", nativeQuery=true)
	Integer receiveraccno(@Param("Rrecieveraccountno") int Rrecieveraccountno);

}
