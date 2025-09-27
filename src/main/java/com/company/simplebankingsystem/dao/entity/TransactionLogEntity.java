package com.company.simplebankingsystem.dao.entity;

import com.company.simplebankingsystem.enums.OperationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
@Entity
@Table(name = "transaction_logs")
public class TransactionLogEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    Long accountId;
    BigDecimal amount;
    @Enumerated(EnumType.STRING)
    OperationType operation;
    @CreationTimestamp
    LocalDateTime timestamp;
}
