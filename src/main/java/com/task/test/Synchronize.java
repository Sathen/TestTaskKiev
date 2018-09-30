package com.task.test;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class Synchronize {
    private Lock lock;

    public Synchronize() {
        lock = new ReentrantLock();
    }

    public Lock getLock() {
        return lock;
    }
}
