package com.task.test.repo;

import com.task.test.model.Operation;

import java.util.List;

public interface HistoryRepository {
    List<Operation> getOperationsByUser(Long userId);

    boolean saveOperation(Operation ope);
}
