package io.hhplus.tdd.point.domain;

public interface UserPointRepository {
    UserPoint selectById(Long id) throws InterruptedException;
    UserPoint insertOrUpdate(Long id, Long amount) throws InterruptedException;
}
