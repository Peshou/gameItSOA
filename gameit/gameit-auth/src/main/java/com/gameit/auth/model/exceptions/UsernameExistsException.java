package com.gameit.auth.model.exceptions;

public class UsernameExistsException extends Exception {
    private static final String USERNAME_EXISTS_MESSAGE = "A user is already registered with this username";

    public UsernameExistsException() {
        super(USERNAME_EXISTS_MESSAGE);
    }

    public UsernameExistsException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return USERNAME_EXISTS_MESSAGE;
    }
}
