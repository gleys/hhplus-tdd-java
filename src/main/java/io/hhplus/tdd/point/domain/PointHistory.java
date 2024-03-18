package io.hhplus.tdd.point.domain;

public record PointHistory(
    Long id,
    Long userId,
    TransactionType type,
    Long amount,
    Long timeMillis
) {

    public PointHistory(final Long id, final Long userId, final TransactionType type, final Long amount, final Long timeMillis) {
        if (isAmountMinus(amount)) {
            throw new IllegalArgumentException();
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