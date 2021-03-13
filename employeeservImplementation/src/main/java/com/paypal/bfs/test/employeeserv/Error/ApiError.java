package com.paypal.bfs.test.employeeserv.Error;

import lombok.Data;
import org.springframework.http.HttpStatus;

/*
Class to model the API errors and embed as multilist in response.
 */
@Data
public class ApiError{

    private final HttpStatus status;
    private final String errorDetail;

}