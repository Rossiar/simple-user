package com.henderson.simpleuser.exceptions;

/**
 * Created by ross on 17/10/16.
 */
public class AlreadyExistsException extends Exception {

    public AlreadyExistsException() {
        super();
    }

    public AlreadyExistsException(String s) {
        super(s);
    }
}
