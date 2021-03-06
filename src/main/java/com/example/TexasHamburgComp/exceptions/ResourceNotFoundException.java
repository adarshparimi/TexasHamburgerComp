package com.example.TexasHamburgComp.exceptions;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
    public ResourceNotFoundException(Throwable cause){
        super(cause);
    }
    public ResourceNotFoundException(String message){
        super(message);
    }
}
