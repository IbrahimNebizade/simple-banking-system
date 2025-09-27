package com.company.simplebankingsystem.dao.repository;

import com.company.simplebankingsystem.dao.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<AccountEntity,Long> {
}
