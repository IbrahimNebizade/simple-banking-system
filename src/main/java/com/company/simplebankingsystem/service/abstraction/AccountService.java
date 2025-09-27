package com.company.simplebankingsystem.service.abstraction;

import com.company.simplebankingsystem.dto.request.CreateAccountRequest;
import com.company.simplebankingsystem.dto.request.DepositRequest;
import com.company.simplebankingsystem.dto.request.TransferRequest;
import com.company.simplebankingsystem.dto.request.WithdrawRequest;
import com.company.simplebankingsystem.dto.response.CreateAccountResponse;
import com.company.simplebankingsystem.dto.response.DepositResponse;
import com.company.simplebankingsystem.dto.response.TransferResponse;
import com.company.simplebankingsystem.dto.response.WithdrawResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

public interface AccountService {
     CreateAccountResponse create(@RequestBody CreateAccountRequest request);
    DepositResponse deposit(Long accountId,BigDecimal amount);

    WithdrawResponse withdraw(Long accountId,BigDecimal amount);

    TransferResponse transfer(TransferRequest request);
}
