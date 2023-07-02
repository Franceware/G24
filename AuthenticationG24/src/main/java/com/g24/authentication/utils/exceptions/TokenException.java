package com.g24.authentication.utils.exceptions;

public class TokenException extends Exception 
{ 
    public TokenException(String errorMessage) 
    {
        super(errorMessage);
    }
}