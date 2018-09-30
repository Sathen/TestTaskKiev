package com.task.test.model;

import java.math.BigDecimal;

public class User {

    private BigDecimal amount;
    private Long id;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
