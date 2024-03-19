package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;

import java.util.List;

public interface PointHistoryRepository {
    PointHistory insert(
            Long id,
            Long amount,
            TransactionType transactionType,
            Long updateMillis
    ) throws InterruptedException;

    List<PointHistory> selectAllByUserId(Long userId);




}
