package com.task.test;

import com.task.test.model.User;
import com.task.test.repo.UserRepository;
import com.task.test.service.CommandWalletService;
import com.task.test.service.QueryWalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {
    private static final long USER_ID = 1L;
    private static final BigDecimal DEFAULT_AMOUNT = BigDecimal.valueOf(100L);
    private static final int THREADS = 100;
    private static final BigDecimal ADD_VALUE = BigDecimal.valueOf(50L);

    @Autowired
    private CommandWalletService commandWalletService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QueryWalletService queryWalletService;

    @Test
    public void testDepositInFewThreads() throws InterruptedException, ExecutionException {
        User user = new User();
        user.setId(USER_ID);
        user.setAmount(DEFAULT_AMOUNT);

        userRepository.saveUser(user);
        List<TaskDeposit> tasks = IntStream.range(0, THREADS)
                .mapToObj(value -> new TaskDeposit()).collect(Collectors.toList());

        ExecutorService executorService = Executors.newFixedThreadPool(THREADS);
        List<Future<Boolean>> futureList = executorService.invokeAll(tasks);
        for (Future<Boolean> booleanFuture : futureList) {
            assertTrue(booleanFuture.get());
        }
        assertEquals(DEFAULT_AMOUNT.add(ADD_VALUE.multiply(BigDecimal.valueOf(THREADS))),
                queryWalletService.userAmount(USER_ID));
    }

    private class TaskDeposit implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            return commandWalletService.deposit(USER_ID, ADD_VALUE);
        }
    }

}
