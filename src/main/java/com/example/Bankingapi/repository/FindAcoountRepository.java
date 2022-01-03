package com.example.Bankingapi.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.Bankingapi.entity.AddAmount;


public interface FindAcoountRepository  extends JpaRepository <AddAmount, Integer>  {
	@Query(value="select accountno from usersdb.addamount u where u.accountno =:Anumber", nativeQuery=true)
	Integer getaccountnumber(@Param("Anumber") int Anumber);

}
