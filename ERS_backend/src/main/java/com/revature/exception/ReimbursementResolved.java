package com.revature.exception;

public class ReimbursementResolved extends Exception{
    public ReimbursementResolved() {
        super();
    }

    public ReimbursementResolved(String message) {
        super(message);
    }

    public ReimbursementResolved(String message, Throwable cause) {
        super(message, cause);
    }

    public ReimbursementResolved(Throwable cause) {
        super(cause);
    }

    protected ReimbursementResolved(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}