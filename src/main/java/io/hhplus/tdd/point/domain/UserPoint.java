package io.hhplus.tdd.point.domain;

import java.time.Instant;

public record UserPoint(
    Long id,
    Long point,
    Long updateMillis
) {

    public UserPoint pay(final long paymentTotal,
                         final long purchasedTime) {
        if (this.point() < paymentTotal) {
            throw new IllegalArgumentException("현재 잔고가 부족 합니다.");
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
