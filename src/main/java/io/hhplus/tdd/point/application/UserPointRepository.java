package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;

public interface UserPointRepository {
    UserPoint selectById(Long id) throws InterruptedException;
    UserPoint insertOrUpdate(Long id, Long amount) throws InterruptedException;
}
