package com.may.ars.handler;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class ExceptionHandler implements ResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // your error handling here
        System.out.println("왔");
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // ...'

        System.out.println("왔냐2");
        return false;

    }
}