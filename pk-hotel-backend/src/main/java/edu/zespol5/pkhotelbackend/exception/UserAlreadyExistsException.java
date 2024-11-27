package edu.zespol5.pkhotelbackend.exception;

public class UserAlreadyExistsException extends IllegalArgumentException {
    public UserAlreadyExistsException(String msg) {super(msg);}
}
