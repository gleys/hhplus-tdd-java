package io.hhplus.tdd.point.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserPointTest {

    @Test
    void 구매_포인트_보다_잔여_포인트가_적을_경우_예외를_발생한다() {
        //given
        UserPoint userPoint = new UserPoint(1L, 10L, 0L);
        long paymentTotal = 100L;
        long currentTime = 2L;

        //when & then
        Assertions.assertThatThrownBy(() -> userPoint.pay(paymentTotal, currentTime))
                .isInstanceOf(IllegalArgumentException.class);
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
        Assertions.assertThat(renewUserPoint.point()).isEqualTo(7);
        Assertions.assertThat(renewUserPoint.updateMillis()).isEqualTo(currentTime);
        Assertions.assertThat(renewUserPoint.id()).isEqualTo(1L);
    }

}