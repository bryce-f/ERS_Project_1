package com.revature.exception;

public class ReimbursementFinalized extends Exception{
    public ReimbursementFinalized() {
        super();
    }

    public ReimbursementFinalized(String message) {
        super(message);
    }

    public ReimbursementFinalized(String message, Throwable cause) {
        super(message, cause);
    }

    public ReimbursementFinalized(Throwable cause) {
        super(cause);
    }

    protected ReimbursementFinalized(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}