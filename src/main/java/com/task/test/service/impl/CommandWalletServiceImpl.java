package com.task.test.service.impl;

import com.task.test.Synchronize;
import com.task.test.model.Operation;
import com.task.test.model.OperationType;
import com.task.test.model.User;
import com.task.test.repo.HistoryRepository;
import com.task.test.repo.UserRepository;
import com.task.test.service.CommandWalletService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.locks.Lock;

@Service
public class CommandWalletServiceImpl implements CommandWalletService {

    private UserRepository userRepository;
    private HistoryRepository historyRepository;
    private Synchronize synchronize;

    public CommandWalletServiceImpl(UserRepository userRepository, HistoryRepository historyRepository, Synchronize synchronize) {
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
        this.synchronize = synchronize;
    }


    @Override
    public boolean deposit(Long userId, BigDecimal amount) {
        Lock lock = synchronize.getLock();
        lock.lock();
        Operation.Builder operationBuilder = startOperation(userId, amount, OperationType.deposit);
        try {
            User user = userRepository.getUser(userId);
            BigDecimal userAmount = user.getAmount();
            user.setAmount(userAmount.add(amount));
            operationBuilder.setSeccess(userRepository.saveUser(user));
        } catch (Exception e) {
            operationBuilder.setSeccess(false);
        } finally {
            lock.unlock();
        }
        Operation operation = operationBuilder.build();
        historyRepository.saveOperation(operation);
        return operation.isSuccess();
    }

    @Override
    public boolean withdraw(Long userId, BigDecimal amount) {
        Lock lock = synchronize.getLock();
        lock.lock();
        Operation.Builder operationBuilder = startOperation(userId, amount, OperationType.deposit);
        try {
            User user = userRepository.getUser(userId);
            BigDecimal userAmount = user.getAmount();
            user.setAmount(userAmount.subtract(amount));
            operationBuilder.setSeccess(userRepository.saveUser(user));
        } catch (Exception e) {
            operationBuilder.setSeccess(false);
        } finally {
            lock.unlock();
        }
        Operation operation = operationBuilder.build();
        historyRepository.saveOperation(operation);
        return operation.isSuccess();
    }

    private Operation.Builder startOperation(Long userId, BigDecimal amount, OperationType type) {
        return Operation
                .builder()
                .setUserId(userId)
                .setAmount(amount)
                .setOperation(type)
                .setTimestamp(new Date().toInstant().toEpochMilli());
    }
}
