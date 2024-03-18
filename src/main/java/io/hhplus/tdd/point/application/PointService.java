package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.domain.UserPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PointService {

    private final UserPointRepository userPointRepository;
    private final Timer timer;
    public UserPoint paymentProcess(final long userId, final long paymentTotal) throws InterruptedException {
        UserPoint renewUserPoint = userPointRepository.selectById(userId)
                                      .pay(paymentTotal, timer.getCurrentTime());
        return userPointRepository.insertOrUpdate(renewUserPoint.id(), renewUserPoint.point());
    }



}