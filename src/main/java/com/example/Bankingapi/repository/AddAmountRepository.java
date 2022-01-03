package com.example.Bankingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Bankingapi.entity.AddAmount;

public interface AddAmountRepository extends JpaRepository <AddAmount, Long>  {

}
