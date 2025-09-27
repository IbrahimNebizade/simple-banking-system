package com.company.simplebankingsystem.controller;

import com.company.simplebankingsystem.dto.request.CreateAccountRequest;
import com.company.simplebankingsystem.dto.request.DepositRequest;
import com.company.simplebankingsystem.dto.request.TransferRequest;
import com.company.simplebankingsystem.dto.request.WithdrawRequest;
import com.company.simplebankingsystem.dto.response.CreateAccountResponse;
import com.company.simplebankingsystem.dto.response.DepositResponse;
import com.company.simplebankingsystem.dto.response.TransferResponse;
import com.company.simplebankingsystem.dto.response.WithdrawResponse;
import com.company.simplebankingsystem.service.abstraction.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("v1/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @PostMapping("/create")
    @ResponseStatus(CREATED)
    public CreateAccountResponse create(@RequestBody CreateAccountRequest request) {
        return accountService.create(request);
    }

    @PostMapping("/deposit/{accountId}")
    @ResponseStatus(OK)
    public DepositResponse deposit(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        return accountService.deposit(accountId,amount);
    }

    @DeleteMapping("/withdraw/{accountId}")
    @ResponseStatus(OK)
    public WithdrawResponse withdraw(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        return accountService.withdraw(accountId,amount);
    }

    @PutMapping("/transfer")
    @ResponseStatus(OK)
    public TransferResponse transfer(@Valid @RequestBody TransferRequest request) {
        return accountService.transfer(request);
    }

}
