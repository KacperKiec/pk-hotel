package edu.zespol5.pkhotelbackend.exception;

public class UserAuthenticationFailedException extends IllegalArgumentException {
    public UserAuthenticationFailedException(String msg) {super(msg);}
}
