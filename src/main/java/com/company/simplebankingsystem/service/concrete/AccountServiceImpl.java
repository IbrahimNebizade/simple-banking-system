package com.company.simplebankingsystem.service.concrete;

import com.company.simplebankingsystem.dao.entity.AccountEntity;
import com.company.simplebankingsystem.dao.entity.TransactionLogEntity;
import com.company.simplebankingsystem.dao.entity.UserEntity;
import com.company.simplebankingsystem.dao.repository.AccountRepository;
import com.company.simplebankingsystem.dao.repository.TransactionLogRepository;
import com.company.simplebankingsystem.dao.repository.UserRepository;
import com.company.simplebankingsystem.dto.request.CreateAccountRequest;
import com.company.simplebankingsystem.dto.request.DepositRequest;
import com.company.simplebankingsystem.dto.request.TransferRequest;
import com.company.simplebankingsystem.dto.request.WithdrawRequest;
import com.company.simplebankingsystem.dto.response.CreateAccountResponse;
import com.company.simplebankingsystem.dto.response.DepositResponse;
import com.company.simplebankingsystem.dto.response.TransferResponse;
import com.company.simplebankingsystem.dto.response.WithdrawResponse;
import com.company.simplebankingsystem.exception.NotFoundException;
import com.company.simplebankingsystem.mapper.AccountMapper;
import com.company.simplebankingsystem.service.abstraction.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.company.simplebankingsystem.enums.OperationType.DEPOSIT;
import static com.company.simplebankingsystem.enums.OperationType.TRANSFER;
import static com.company.simplebankingsystem.enums.OperationType.WITHDRAW;
import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    TransactionLogRepository transactionLogRepository;
    UserRepository userRepository;

    private AccountEntity findAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Override
    public CreateAccountResponse create(CreateAccountRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        AccountEntity account = AccountEntity.builder()
                .userId(user.getId())
                .balance(BigDecimal.ZERO)
                .build();
        accountRepository.save(account);
        return AccountMapper.entityToResponse(account);
    }

    @Transactional
    @Override
    public DepositResponse deposit(DepositRequest request) {
        log.info("deposit started for accountId: {}", request.getAccountId());
        AccountEntity account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        TransactionLogEntity logEntity = TransactionLogEntity.builder()
                .accountId(request.getAccountId())
                .operation(DEPOSIT)
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .build();
        transactionLogRepository.save(logEntity);
        log.info("deposit finished for accountId: {}", request.getAccountId());
        return AccountMapper.entityToDepositResponse(account);
    }

    @Transactional
    @Override
    public WithdrawResponse withdraw(WithdrawRequest request) {
        log.info("withdraw started for accountId: {}", request.getAccountId());
        AccountEntity account = findAccount(request.getAccountId());

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        TransactionLogEntity logEntity = TransactionLogEntity.builder()
                .accountId(request.getAccountId())
                .operation(WITHDRAW)
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .build();
        transactionLogRepository.save(logEntity);
        log.info("withdraw finished for accountId: {}", request.getAccountId());
        return WithdrawResponse.builder()
                .balance(account.getBalance())
                .userId(account.getUserId())
                .build();
    }

    @Transactional
    @Override
    public TransferResponse transfer(TransferRequest request) {
        log.info("transfer started from {} to {}", request.getFromAccountId(), request.getToAccountId());
        AccountEntity fromAccount = findAccount(request.getFromAccountId());
        AccountEntity toAccount = findAccount(request.getToAccountId());

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        TransactionLogEntity logEntity = TransactionLogEntity.builder()
                .accountId(request.getFromAccountId())
                .operation(TRANSFER)
                .amount(request.getAmount())
                .timestamp(LocalDateTime.now())
                .build();
        transactionLogRepository.save(logEntity);
        log.info("transfer finished from {} to {}", request.getFromAccountId(), request.getToAccountId());
        return AccountMapper.requestToTransferResponse(request);
    }

}
