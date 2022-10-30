package com.bridgelabz.userregistrationapp.exception;

public class InvalidLoginCredentialsException extends RuntimeException{
    public InvalidLoginCredentialsException(String msg){
        super(msg);
    }
}
