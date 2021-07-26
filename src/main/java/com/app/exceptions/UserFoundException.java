package com.app.exceptions;

public class UserFoundException extends Exception {
    public UserFoundException() {
        super("tài khoản đã tồn tại");
    }

    public UserFoundException(String message) {
        super(message);
    }
}
