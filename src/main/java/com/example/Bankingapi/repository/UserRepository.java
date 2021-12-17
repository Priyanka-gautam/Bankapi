package com.example.Bankingapi.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Bankingapi.entity.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

}
