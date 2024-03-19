package io.hhplus.tdd.point.domain;

import io.hhplus.tdd.point.error.NotEnoughAmountException;

public record PointHistory(
    Long id,
    Long userId,
    TransactionType type,
    Long amount,
    Long timeMillis
) {

    public PointHistory(final Long id, final Long userId, final TransactionType type, final Long amount, final Long timeMillis) {
        if (isAmountMinus(amount)) {
            throw NotEnoughAmountException.EXCEPTION;
        }
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.timeMillis = timeMillis;
    }

    private boolean isAmountMinus(final Long amount) {
        return amount < 0;
    }

}