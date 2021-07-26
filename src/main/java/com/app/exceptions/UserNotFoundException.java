package com.app.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("tài khoản không tồn tại");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
