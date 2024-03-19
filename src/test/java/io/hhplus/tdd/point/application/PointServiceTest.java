package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.error.InvalidAmountException;
import io.hhplus.tdd.point.error.NotEnoughAmountException;
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

class PointServiceTest {
    @Mock
    private PointService pointService;
    private PointHistoryRepository pointHistoryRepository;
    private final FakeTimer timer = new FakeTimer();
    @BeforeEach
    void setup() {
        Map<Long, UserPoint> table = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            table.put((long) i + 1,
                    new UserPoint((long) i + 1, (long) i, System.currentTimeMillis()));
        }

        UserPointRepository userPointRepository = new FakeUserPointRepository(table);
        this.pointHistoryRepository = new FakePointHistoryRepository();

        this.pointService =
            new PointService(userPointRepository,
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
                          .isInstanceOf(NotEnoughAmountException.class)
                        .hasMessageMatching(NotEnoughAmountException.EXCEPTION.getMessage());

    }

    @Test
    void 결제_금액은_0_보다_커야한다() throws InterruptedException {
        //given
        long userId = 5;
        long paymentTotal = 0;

        //when & then
        assertThatThrownBy(() -> pointService.paymentProcess(userId, paymentTotal))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessageMatching(InvalidAmountException.EXCEPTION.getMessage());

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
        assertThat(userPoint.point()).isEqualTo(1);
        assertThat(oldPointHistories.size()).isLessThan(renewedPointHistories.size());
        assertThat(renewedPointHistories.get(size).type()).isEqualTo(TransactionType.USE);
    }

    @Test
    void 충전_금액은_반드시_0_보다_커야_한다() {
        //given
        long userId = 5;
        long chargeTotal = 0;

        //when & then
        assertThatThrownBy(() -> pointService.chargeProcess(userId, chargeTotal))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessageMatching(InvalidAmountException.EXCEPTION.getMessage());

    }

    @Test
    void 충전_금액은_반드시_0_보다_크면_성공하고_기록을_남긴다() throws InterruptedException {
        //given
        long userId = 0;
        long chargeTotal = 50;
        List<PointHistory> oldPointHistories = this.pointHistoryRepository.selectAllByUserId(userId);

        //when
        UserPoint userPoint = pointService.chargeProcess(userId, chargeTotal);
        List<PointHistory> renewedPointHistories = this.pointHistoryRepository.selectAllByUserId(userId);
        int size = renewedPointHistories.size() - 1;

        //then
        assertThat(userPoint.point()).isEqualTo(50L);
        assertThat(renewedPointHistories.size()).isGreaterThan(oldPointHistories.size());
        assertThat(renewedPointHistories.get(size).type()).isEqualTo(TransactionType.CHARGE);
    }


}