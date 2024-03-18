package io.hhplus.tdd.point.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointHistoryTest {

    @Test
    void 타입에_상관없이_금액이_음수일_때_예외가_발생_한다() {
        //given
        Long paymentTotal = -10L;

        //when & then
        Assertions.assertThatThrownBy(() -> new PointHistory(
                1L, 1L, TransactionType.CHARGE, paymentTotal, 1L))
                .isInstanceOf(IllegalArgumentException.class);

        Assertions.assertThatThrownBy(() -> new PointHistory(
                        1L, 1L, TransactionType.USE, paymentTotal, 1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

}