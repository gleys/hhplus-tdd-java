package io.hhplus.tdd.point.application;

import io.hhplus.tdd.point.domain.UserPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeUserPointRepository implements UserPointRepository {
    private Map<Long, UserPoint> table = new HashMap<>();

    @Override
    public UserPoint selectById(Long id) throws InterruptedException {
        UserPoint userPoint = table.get(id);

        if (userPoint == null) {
            return new UserPoint(id, 0L, System.currentTimeMillis());
        }
        return userPoint;
    }

    @Override
    public UserPoint insertOrUpdate(Long id, Long amount) throws InterruptedException {
        UserPoint userPoint = new UserPoint(id, amount, System.currentTimeMillis());
        table.put(id, userPoint);

        return userPoint;
    }

    public void setUsers(List<UserPoint> users) {
        users.forEach(user -> table.put(user.id(), user));
    }
}
