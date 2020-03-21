package com.adrianstypinski.exceptions;

import java.io.IOException;

public class ResponseCodeException extends Exception {
    public ResponseCodeException() {

    }

    public ResponseCodeException(String message) {
        super(message);
    }
}
