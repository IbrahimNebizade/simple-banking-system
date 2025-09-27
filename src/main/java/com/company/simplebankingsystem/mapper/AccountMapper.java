package com.company.simplebankingsystem.mapper;


import com.company.simplebankingsystem.dao.entity.AccountEntity;
import com.company.simplebankingsystem.dto.request.TransferRequest;
import com.company.simplebankingsystem.dto.response.CreateAccountResponse;
import com.company.simplebankingsystem.dto.response.DepositResponse;
import com.company.simplebankingsystem.dto.response.TransferResponse;

import java.time.LocalDateTime;

import static com.company.simplebankingsystem.enums.OperationType.TRANSFER;

public class AccountMapper {

    public static CreateAccountResponse entityToResponse(AccountEntity account) {
        return CreateAccountResponse.builder()
                .id(account.getId())
                .userId(account.getUserId())
                .balance(account.getBalance())
                .build();
    }

    public static TransferResponse requestToTransferResponse(TransferRequest request) {
        return TransferResponse.builder()
                .fromAccountId(request.getFromAccountId())
                .toAccountId(request.getToAccountId())
                .amount(request.getAmount())
                .operation(TRANSFER)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static DepositResponse entityToDepositResponse(AccountEntity account){
        return DepositResponse.builder()
                .id(account.getId())
                .userId(account.getUserId())
                .balance(account.getBalance())
                .build();
    }

}
