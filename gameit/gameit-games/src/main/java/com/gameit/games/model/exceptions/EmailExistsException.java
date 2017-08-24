package com.gameit.games.model.exceptions;

public class EmailExistsException extends Exception {
    private static final String EMAIL_EXISTS_MESSAGE = "A user is already registered with this email";

    public EmailExistsException() {
        super(EMAIL_EXISTS_MESSAGE);
    }

    public EmailExistsException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return EMAIL_EXISTS_MESSAGE;
    }
}
