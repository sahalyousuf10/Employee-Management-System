package com.example.sahal.Springbootmultithreading2.exception;


public class EmployeeAlreadyExistException extends RuntimeException{
    public EmployeeAlreadyExistException(String message){
        super(message);
    }
}
