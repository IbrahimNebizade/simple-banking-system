package com.company.simplebankingsystem.service.concrete;

import com.company.simplebankingsystem.dao.entity.TransactionLogEntity;
import com.company.simplebankingsystem.dao.repository.TransactionLogRepository;
import com.company.simplebankingsystem.dto.request.DepositRequest;
import com.company.simplebankingsystem.dto.request.TransferRequest;
import com.company.simplebankingsystem.dto.request.WithdrawRequest;
import com.company.simplebankingsystem.dto.response.TransactionResponse;
import com.company.simplebankingsystem.service.abstraction.TransactionLogService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.company.simplebankingsystem.enums.OperationType.DEPOSIT;
import static com.company.simplebankingsystem.enums.OperationType.TRANSFER;
import static com.company.simplebankingsystem.enums.OperationType.WITHDRAW;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TransactionLogImpl implements TransactionLogService {
    TransactionLogRepository repository;

    @Override
    public List<TransactionResponse> findTransactionByAccountId(Long accountId) {
        List<TransactionLogEntity> transactions = repository.findByAccountId(accountId);
        return transactions.stream()
                .map(entity -> TransactionResponse.builder()
                        .id(entity.getId())
                        .accountId(entity.getAccountId())
                        .amount(entity.getAmount())
                        .operation(entity.getOperation())
                        .timestamp(entity.getTimestamp())
                        .build())
                .toList();
    }

    @Override
    public void depositLog(Long accountId, BigDecimal amount) {
        TransactionLogEntity logEntity = TransactionLogEntity.builder()
                .accountId(accountId)
                .operation(DEPOSIT)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .build();
        repository.save(logEntity);
    }

    @Override
    public void withdrawLog(Long accountId, BigDecimal amount) {
        TransactionLogEntity logEntity = TransactionLogEntity.builder()
                .accountId(accountId)
                .operation(WITHDRAW)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .build();
        repository.save(logEntity);
    }

}
