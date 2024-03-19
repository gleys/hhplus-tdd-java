package io.hhplus.tdd.point.error;

public class NotEnoughAmountException extends ServiceException {
    public static ServiceException EXCEPTION = new NotEnoughAmountException();

    public NotEnoughAmountException() {
        super("현재 잔고가 부족 합니다.");
    }
}
