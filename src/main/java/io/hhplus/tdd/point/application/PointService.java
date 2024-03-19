package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.PointHistory;
import io.hhplus.tdd.point.domain.TransactionType;
import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.error.InvalidAmountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public UserPoint getUserPoint(final long userId) throws InterruptedException {
        return this.userPointRepository.selectById(userId);
    }

    public List<PointHistory> getPointHistories(final long userId) throws InterruptedException {
        return this.pointHistoryRepository.selectAllByUserId(userId);
    }

    private void verifyAmount(final long amount) {
        if (amount <= 0) {
            throw InvalidAmountException.EXCEPTION;
        }
    }



}