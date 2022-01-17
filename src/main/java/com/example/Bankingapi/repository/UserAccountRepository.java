package com.example.Bankingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Bankingapi.entity.UserAccount;

public interface UserAccountRepository extends JpaRepository <UserAccount, Long> {

}
