package com.task.test.model;

import lombok.Getter;

import java.math.BigDecimal;


public class Operation {

    @Getter
    private Long userId;
    private OperationType operation;
    private BigDecimal amount;
    private Long timestamp;
    @Getter
    private boolean success;

    private Operation(Long userId, OperationType operation, BigDecimal amount, Long timestamp, boolean success) {
        this.userId = userId;
        this.operation = operation;
        this.amount = amount;
        this.timestamp = timestamp;
        this.success = success;
    }

    public static Operation.Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private OperationType operation;
        private BigDecimal amount;
        private Long timestamp;
        private boolean seccess;

        private Builder() {
        }

        public Builder setOperation(OperationType operation) {
            this.operation = operation;
            return this;
        }

        public Builder setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setSeccess(boolean success) {
            this.seccess = success;
            return this;
        }

        public Operation build() {
            return new Operation(userId, operation, amount, timestamp, seccess);
        }
    }

    @Override
    public String toString() {
        return "Operation{" +
                "userId=" + userId +
                ", operation=" + operation +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", success=" + success +
                '}';
    }
}
