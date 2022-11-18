package com.example.awtjwtdemo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PasswordDoNotMatchError extends ResponseStatusException {
    public  PasswordDoNotMatchError(){
        super(HttpStatus.BAD_REQUEST,"Password do not match");
    }
}
