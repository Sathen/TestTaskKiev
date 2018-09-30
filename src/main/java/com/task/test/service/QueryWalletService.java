package com.task.test.service;

import com.task.test.model.Operation;

import java.math.BigDecimal;
import java.util.List;

public interface QueryWalletService {
    BigDecimal userAmount(Long userId);

    List<Operation> history(Long userId);

}
