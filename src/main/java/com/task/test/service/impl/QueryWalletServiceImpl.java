package com.task.test.service.impl;

import com.task.test.Synchronize;
import com.task.test.model.Operation;
import com.task.test.repo.HistoryRepository;
import com.task.test.repo.UserRepository;
import com.task.test.service.QueryWalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;

@Service
public class QueryWalletServiceImpl implements QueryWalletService {

    private UserRepository userRepository;
    private HistoryRepository historyRepository;
    private Lock lock;

    public QueryWalletServiceImpl(UserRepository userRepository, HistoryRepository historyRepository, Synchronize lock) {
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.lock = lock.getLock();
    }


    @Override
    public BigDecimal userAmount(Long userId) {
        BigDecimal result;
        lock.lock();
        try {
            result = userRepository.getUser(userId).getAmount();
        } finally {
            lock.unlock();
        }
        return result;
    }

    @Override
    public List<Operation> history(Long userId) {
        lock.lock();
        List<Operation> operations;
        try {
            operations = historyRepository.getOperationsByUser(userId);
        } finally {
            lock.unlock();
        }
        return operations;
    }
}
