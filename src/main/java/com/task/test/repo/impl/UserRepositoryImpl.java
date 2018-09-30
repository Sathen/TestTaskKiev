package com.task.test.repo.impl;

import com.task.test.repo.UserRepository;
import com.task.test.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private Map<Long, User> userMap;

    public UserRepositoryImpl() {
        userMap = new ConcurrentHashMap<>();
    }

    @Override
    public User getUser(Long userId) {
        return userMap.get(userId);
    }

    @Override
    public boolean saveUser(User user) {
        return Objects.nonNull(userMap.put(user.getId(), user));
    }
}
