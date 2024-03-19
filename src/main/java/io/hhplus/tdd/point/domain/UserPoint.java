package io.hhplus.tdd.point.domain;

import io.hhplus.tdd.point.error.NotEnoughAmountException;

import java.time.Instant;

public record UserPoint(
    Long id,
    Long point,
    Long updateMillis
) {

    public UserPoint pay(final long paymentTotal,
                         final long purchasedTime) {
        if (this.point() < paymentTotal) {
            throw NotEnoughAmountException.EXCEPTION;
        }
        return new UserPoint(
                this.id,
                this.point - paymentTotal,
                purchasedTime);
    }

    public UserPoint charge(final long chargeTotal,
                            final long purchasedTime) {
        return new UserPoint(
                this.id,
                this.point + chargeTotal,
                purchasedTime);
    }
}
