package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointService {

    private final UserPointRepository userPointRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final Timer timer;
    public UserPoint paymentProcess(final long userId, final long paymentTotal) throws InterruptedException {
        this.verifyAmount(paymentTotal);
        UserPoint renewedUserPoint = this.userPointRepository.selectById(userId)
                                      .pay(paymentTotal, timer.getCurrentTime());

        pointHistoryRepository.insert(userId, paymentTotal, TransactionType.USE, timer.getCurrentTime());
        return userPointRepository.insertOrUpdate(renewedUserPoint.id(), renewedUserPoint.point());
    }

    public UserPoint chargeProcess(final long userId, final long chargeTotal) throws InterruptedException {
        this.verifyAmount(chargeTotal);
        UserPoint renewedUserPoint = this.userPointRepository.selectById(userId)
                                   .charge(chargeTotal, timer.getCurrentTime());

        pointHistoryRepository.insert(userId, chargeTotal, TransactionType.CHARGE, timer.getCurrentTime());
        return userPointRepository.insertOrUpdate(renewedUserPoint.id(), renewedUserPoint.point());

    }

    private void verifyAmount(final long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("충전 또는 결제 포인트는 0을 초과해야 합니다.");
        }
    }



}