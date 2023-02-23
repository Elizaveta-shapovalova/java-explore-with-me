package ru.practicum.ewm_main.exception;

public class TimeNotValidException extends RuntimeException {
    public TimeNotValidException(String message) {
        super(message);
    }
}
