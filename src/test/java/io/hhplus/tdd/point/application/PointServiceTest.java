package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {
    @Mock
    private PointService pointService;
    private UserPointRepository userPointRepository;
    private PointHistoryRepository pointHistoryRepository;
    private final FakeTimer timer = new FakeTimer();
    @BeforeEach
    void setup() {
        Map<Long, UserPoint> table = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            table.put((long) i + 1,
                    new UserPoint((long) i + 1, (long) i, System.currentTimeMillis()));
        }

        this.userPointRepository = new FakeUserPointRepository(table);
        this.pointHistoryRepository = new FakePointHistoryRepository();

        this.pointService =
            new PointService(this.userPointRepository,
                    this.pointHistoryRepository,
                    this.timer);
    }

    @Test
    void 잔고가_부족할_경우_포인트_사용은_실패하고_기록을_남기지_않는다() {
        /*
         * UserPoint, PointHistory 에서 로직을 테스트 하였기 때문에 application layer 에서는 해당 부분에 대한 검증이 필요 없지 않나요?
         * 그래서 포인트가 부족할 경우 예외 발생한다 이 부분은 UserPoint 에서 검증했기 때문에
         * applicaiton layer 에서는 point history 기록 저장 여부에 초점을 맞추고 테스트 하려 합니다.
         * 이 때 userPointRepository.insertOrUpdate() 호출 여부로 이를 판단하려면 반드시 mock 이 필요하지 않나요?
         */

        //given
        long userId = 1;
        long paymentAmount = 100;

        //when & then
        assertThatThrownBy(() -> pointService.paymentProcess(userId, paymentAmount))
                          .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageMatching("현재 잔고가 부족 합니다.");

    }

    @Test
    void 결제_금액은_0_보다_커야한다() throws InterruptedException {
        //given
        long userId = 5;
        long paymentTotal = 0;

        //when & then
        assertThatThrownBy(() -> pointService.paymentProcess(userId, paymentTotal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("충전 또는 결제 포인트는 0을 초과해야 합니다.");

    }
    @Test
    void 구매시_잔고가_충분할_경우_포인트_내역_갱신_된다() throws InterruptedException {
        //given
        long userId = 5;
        long paymentTotal = 3;
        List<PointHistory> oldPointHistories = pointHistoryRepository.selectAllByUserId(userId);

        //when
        UserPoint userPoint = pointService.paymentProcess(userId, paymentTotal);
        List<PointHistory> renewedPointHistories = pointHistoryRepository.selectAllByUserId(userId);
        int size = renewedPointHistories.size() - 1;

        //then
        assertThat(oldPointHistories.size()).isLessThan(renewedPointHistories.size());
        assertThat(renewedPointHistories.get(size).type()).isEqualTo(TransactionType.USE);
    }



}