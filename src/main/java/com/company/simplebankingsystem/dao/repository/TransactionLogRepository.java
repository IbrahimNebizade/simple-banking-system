package com.company.simplebankingsystem.dao.repository;

import com.company.simplebankingsystem.dao.entity.TransactionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepository extends JpaRepository<TransactionLogEntity,Long> {
}
