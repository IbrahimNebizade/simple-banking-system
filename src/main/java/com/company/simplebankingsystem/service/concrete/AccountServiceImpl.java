package com.company.simplebankingsystem.service.concrete;

import com.company.simplebankingsystem.dao.entity.AccountEntity;
import com.company.simplebankingsystem.dao.entity.UserEntity;
import com.company.simplebankingsystem.dao.repository.AccountRepository;
import com.company.simplebankingsystem.dao.repository.UserRepository;
import com.company.simplebankingsystem.dto.request.CreateAccountRequest;
import com.company.simplebankingsystem.dto.request.TransferRequest;
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

import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    UserRepository userRepository;
    TransactionLogImpl tranLogService;

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
    public DepositResponse deposit(Long accountId, BigDecimal amount) {
        log.info("deposit started for accountId: {}", accountId);
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        tranLogService.depositLog(accountId, amount);
        log.info("deposit finished for accountId: {}", accountId);
        return AccountMapper.entityToDepositResponse(account);
    }

    @Transactional
    @Override
    public WithdrawResponse withdraw(Long accountId, BigDecimal amount) {
        log.info("withdraw started for accountId: {}", accountId);
        AccountEntity account = findAccount(accountId);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        tranLogService.withdrawLog(accountId, amount);
        log.info("withdraw finished for accountId: {}", accountId);
        return WithdrawResponse.builder()
                .balance(account.getBalance())
                .userId(account.getUserId())
                .build();
    }

    @Transactional
    @Override
    public TransferResponse transfer(TransferRequest request) {
        log.info("transfer started from {} to {}", request.getFromAccountId(), request.getToAccountId());
        withdraw(request.getFromAccountId(), request.getAmount());
        deposit(request.getToAccountId(), request.getAmount());
        log.info("transfer finished from {} to {}", request.getFromAccountId(), request.getToAccountId());
        return AccountMapper.requestToTransferResponse(request);
    }

}
