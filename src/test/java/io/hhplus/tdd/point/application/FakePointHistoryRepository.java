package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class FakePointHistoryRepository implements PointHistoryRepository {
    private List<PointHistory> table = new ArrayList<>();

    private Long cursor = 1L;

    public PointHistory insert(
            Long id,
            Long amount,
            TransactionType transactionType,
            Long updateMillis
    ) throws InterruptedException {

        PointHistory history = new PointHistory(cursor++, id, transactionType, amount, updateMillis);
        table.add(history);

        return history;
    }

    public List<PointHistory> selectAllByUserId(Long userId) {
        return table.stream()
                   .filter(it -> it.userId().equals(userId))
                   .toList();
    }

}
