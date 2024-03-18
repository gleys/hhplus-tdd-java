package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;
import io.hhplus.tdd.point.domain.UserPointRepository;

public class FakeUserPointRepository implements UserPointRepository {
    @Override
    public UserPoint selectById(final Long id) throws InterruptedException {
        return new UserPoint(id, 5L, 0L);
    }

    @Override
    public UserPoint insertOrUpdate(final Long id, final Long amount) throws InterruptedException {
        return null;
    }
}
