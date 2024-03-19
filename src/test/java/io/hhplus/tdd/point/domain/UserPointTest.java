package io.hhplus.tdd.point.domain;

import io.hhplus.tdd.point.error.NotEnoughAmountException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserPointTest {

    @Test
    void 구매_포인트_보다_잔여_포인트가_적을_경우_예외를_발생한다() {
        //given
        UserPoint userPoint = new UserPoint(1L, 10L, 0L);
        long paymentTotal = 100L;
        long currentTime = 2L;

        //when & then
        assertThatThrownBy(() -> userPoint.pay(paymentTotal, currentTime))
                .isInstanceOf(NotEnoughAmountException.class)
                .hasMessageMatching(NotEnoughAmountException.EXCEPTION.getMessage());
    }

    @Test
    void 구매_성공_시_현재_시간으로_갱신() {
        //given
        UserPoint userPoint = new UserPoint(1L, 10L, 0L);
        long paymentTotal = 3L;
        long currentTime = 2L;

        //when
        UserPoint renewUserPoint = userPoint.pay(paymentTotal, currentTime);

        //then
        assertThat(renewUserPoint.point()).isEqualTo(7);
        assertThat(renewUserPoint.updateMillis()).isEqualTo(currentTime);
        assertThat(renewUserPoint.id()).isEqualTo(1L);
    }

}