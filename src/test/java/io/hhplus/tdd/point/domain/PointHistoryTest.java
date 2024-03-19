package io.hhplus.tdd.point.domain;

import io.hhplus.tdd.point.error.NotEnoughAmountException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PointHistoryTest {

    @Test
    void 타입에_상관없이_금액이_0_보다_작을_때_예외가_발생_한다() {
        //given
        Long paymentTotal = -10L;

        //when & then
        assertThatThrownBy(() -> new PointHistory(
                1L, 1L, TransactionType.CHARGE, paymentTotal, 1L))
                .isInstanceOf(NotEnoughAmountException.class);

        assertThatThrownBy(() -> new PointHistory(
                        1L, 1L, TransactionType.USE, paymentTotal, 1L))
                .isInstanceOf(NotEnoughAmountException.class);
    }

    @Test
    void 타입에_상관없이_금액이_양수이면_성공() {
        //생성에 관한 테스트 때는 when 이 어디에 위치 하는지? 어떤 값을 검증 해야 하는지

        //given
        Long paymentTotal = 10L;
        Long userId = 1L;
        Long chargeHistoryId = 1L;
        Long useHistoryId = 2L;
        Long purchasedTime = 3L;
        Long chargedTime = 2L;

        //when
        PointHistory chargePoint = new PointHistory(chargeHistoryId, userId, TransactionType.CHARGE, paymentTotal, chargedTime);
        PointHistory usePoint = new PointHistory(useHistoryId, userId, TransactionType.USE, paymentTotal, purchasedTime);

        //then
        assertThat(chargePoint.id()).isEqualTo(chargeHistoryId);
        assertThat(chargePoint.userId()).isEqualTo(userId);
        assertThat(chargePoint.type()).isEqualTo(TransactionType.CHARGE);
        assertThat(chargePoint.amount()).isEqualTo(paymentTotal);
        assertThat(chargePoint.timeMillis()).isEqualTo(chargedTime);

        assertThat(usePoint.id()).isEqualTo(useHistoryId);
        assertThat(usePoint.userId()).isEqualTo(userId);
        assertThat(usePoint.type()).isEqualTo(TransactionType.USE);
        assertThat(usePoint.amount()).isEqualTo(paymentTotal);
        assertThat(usePoint.timeMillis()).isEqualTo(purchasedTime);

    }

}