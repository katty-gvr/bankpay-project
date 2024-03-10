package ru.bankpay.bankpay.exception;

public class PaymentException extends RuntimeException {
    public PaymentException(String message) {
        super(message);
    }
}
