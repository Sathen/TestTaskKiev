package com.task.test.service.impl;

import com.task.test.Synchronize;
import com.task.test.model.Operation;
import com.task.test.model.User;
import com.task.test.repo.HistoryRepository;
import com.task.test.repo.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CommandWalletServiceImplTest {
    private static final Long USER_ID = 1L;
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100L);

    @Mock
    private UserRepository userRepository;
    @Mock
    private HistoryRepository historyRepository;
    @Mock
    private Synchronize synchronize;
    @InjectMocks
    private CommandWalletServiceImpl commandWalletService;


    @Before
    public void setUp() {
        when(synchronize.getLock()).thenReturn(new ReentrantLock());
    }

    @Test
    public void testDeposit() throws Exception {
        User user = new User();
        user.setAmount(AMOUNT);
        user.setId(USER_ID);

        ArgumentCaptor<Operation> operationArgumentCaptor = ArgumentCaptor.forClass(Operation.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.getUser(USER_ID)).thenReturn(user);
        when(userRepository.saveUser(isA(User.class))).thenReturn(true);

        commandWalletService.deposit(USER_ID, AMOUNT);

        verify(userRepository).saveUser(userArgumentCaptor.capture());
        verify(historyRepository, times(1)).saveOperation(operationArgumentCaptor.capture());

        assertEquals(AMOUNT.add(AMOUNT), userArgumentCaptor.getValue().getAmount());
        assertTrue(operationArgumentCaptor.getValue().isSuccess());
        assertEquals(USER_ID, operationArgumentCaptor.getValue().getUserId());
    }

    @Test
    public void withdraw() throws Exception {
        User user = new User();
        user.setAmount(AMOUNT);
        user.setId(USER_ID);

        ArgumentCaptor<Operation> operationArgumentCaptor = ArgumentCaptor.forClass(Operation.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.getUser(USER_ID)).thenReturn(user);
        when(userRepository.saveUser(isA(User.class))).thenReturn(true);

        commandWalletService.withdraw(USER_ID, AMOUNT);

        verify(userRepository).saveUser(userArgumentCaptor.capture());
        verify(historyRepository, times(1)).saveOperation(operationArgumentCaptor.capture());

        assertEquals(AMOUNT.subtract(AMOUNT), userArgumentCaptor.getValue().getAmount());
        assertTrue(operationArgumentCaptor.getValue().isSuccess());
        assertEquals(USER_ID, operationArgumentCaptor.getValue().getUserId());
    }

}