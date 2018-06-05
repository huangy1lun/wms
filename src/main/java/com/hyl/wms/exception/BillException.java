package com.hyl.wms.exception;


/**
 * 订单错误
 */
public class BillException extends RuntimeException {

    public BillException() {
    }

    public BillException(String message) {
        super(message);
    }
}
