package com.example.security.repository;

import com.example.security.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <Account, Long>{      //id 타입이 Long
    Account findByUsername(String username);
}
