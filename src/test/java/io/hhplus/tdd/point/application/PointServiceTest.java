package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointServiceTest {
    private PointService pointService;
    @BeforeEach
    void setup() {
        this.pointService = new PointService(
                new FakeUserPointRepository(),
                new FakePointHistoryRepository(),
                new FakeTimer());

    }
    @BeforeEach
    void init() {
        this.pointService =
        new PointService(
                new FakeUserPointRepository(),
                new FakePointHistoryRepository(),
                new FakeTimer());
    }

    @Test
    void 잔고가_부족할_경우_포인트_사용은_실패한다() {
        //fake database 를 만들고 객체를 반환 하면 fake 객체에 의존성이 생기는 테스트 같음, 이게 stub??

        //given

        //when & then
        Assertions.assertThatThrownBy(() -> pointService.paymentProcess(1L, 100L))
                          .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void 잔고가_충분할_경우_포인트_내역_갱신_된다() throws InterruptedException {
        //given
        UserPoint userPoint = pointService.paymentProcess(1, 3);


    }

    @Test
    void 잔고가_부족할_경우_포인트_내역을_남기지_않는다() {

    }


}