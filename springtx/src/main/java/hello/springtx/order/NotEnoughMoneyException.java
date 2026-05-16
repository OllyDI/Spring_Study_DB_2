package hello.springtx.order;

/*
* 결제 잔고 부족시 메시지 알림
* */

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
