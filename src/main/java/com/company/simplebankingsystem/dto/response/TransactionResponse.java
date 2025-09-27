package com.company.simplebankingsystem.dto.response;

import com.company.simplebankingsystem.enums.OperationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class TransactionResponse {
    Long id;
    Long accountId;
    BigDecimal amount;
    OperationType operation;
    LocalDateTime timestamp;
}
