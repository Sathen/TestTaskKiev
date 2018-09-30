package com.task.test.repo;

import com.task.test.model.User;

public interface UserRepository {
    User getUser(Long userId);

    boolean saveUser(User user);
}
