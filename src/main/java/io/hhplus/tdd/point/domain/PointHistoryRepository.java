package io.hhplus.tdd.point.domain;

public interface PointHistoryRepository {
    PointHistory insert(
            Long id,
            Long amount,
            TransactionType transactionType
    );




}
