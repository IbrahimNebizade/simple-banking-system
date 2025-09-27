package com.company.simplebankingsystem.service.abstraction;

import com.company.simplebankingsystem.dto.request.DepositRequest;
import com.company.simplebankingsystem.dto.request.WithdrawRequest;
import com.company.simplebankingsystem.dto.response.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionLogService {
    List<TransactionResponse> findTransactionByAccountId(Long accountId);
    void depositLog(Long accountId, BigDecimal amount);
    void withdrawLog(Long accountId, BigDecimal amount);
}
