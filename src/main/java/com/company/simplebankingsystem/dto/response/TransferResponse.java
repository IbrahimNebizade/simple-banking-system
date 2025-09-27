package com.company.simplebankingsystem.dto.response;

import com.company.simplebankingsystem.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class TransferResponse {
    Long fromAccountId;
    Long toAccountId;
    BigDecimal amount;
    OperationType operation;
    LocalDateTime timestamp;
}
