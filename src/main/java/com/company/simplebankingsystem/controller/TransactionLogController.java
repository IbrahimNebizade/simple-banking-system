package com.company.simplebankingsystem.controller;

import com.company.simplebankingsystem.dto.response.TransactionResponse;
import com.company.simplebankingsystem.service.concrete.TransactionLogImpl;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("v1/transactions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class TransactionLogController {
    TransactionLogImpl tranLogService;

    @GetMapping("find/{accountId}")
    @ResponseStatus(OK)
    public List<TransactionResponse> findTransactionByAccountId(@PathVariable Long accountId) {
        return tranLogService.findTransactionByAccountId(accountId);
    }
}
