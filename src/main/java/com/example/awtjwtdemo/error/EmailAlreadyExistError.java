package com.example.awtjwtdemo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyExistError extends ResponseStatusException {
    public  EmailAlreadyExistError(){
        super(HttpStatus.BAD_REQUEST,"Email Already Exists!");
    }
}
