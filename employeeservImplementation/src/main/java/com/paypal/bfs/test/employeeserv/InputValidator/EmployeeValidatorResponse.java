package com.paypal.bfs.test.employeeserv.InputValidator;

import lombok.Data;
import lombok.Getter;

/**
 * EmployeeValidatorResponse
 */
@Data
@Getter
public class EmployeeValidatorResponse {
    private final boolean isValid;
    private final String errorDetail;
}
