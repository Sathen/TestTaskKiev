package com.task.test.repo.impl;

import com.task.test.model.Operation;
import com.task.test.repo.HistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class HistoryRepositoryImpl implements HistoryRepository {

    private Map<Long, List<Operation>> opearations;

    public HistoryRepositoryImpl() {
        this.opearations = new ConcurrentHashMap<>();
    }

    @Override
    public List<Operation> getOperationsByUser(Long userId) {
        return opearations.get(userId);
    }

    @Override
    public boolean saveOperation(Operation ope) {
        List<Operation> operations = opearations.getOrDefault(ope.getUserId(), new ArrayList<>());
        operations.add(ope);
        return Objects.nonNull(opearations.put(ope.getUserId(), operations));
    }
}
