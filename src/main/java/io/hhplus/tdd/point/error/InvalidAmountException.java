package io.hhplus.tdd.point.error;

public class InvalidAmountException extends ServiceException{
    public static final ServiceException EXCEPTION = new InvalidAmountException();
    public InvalidAmountException() {
        super("충전 또는 결제 포인트는 0을 초과해야 합니다.");
    }
}
