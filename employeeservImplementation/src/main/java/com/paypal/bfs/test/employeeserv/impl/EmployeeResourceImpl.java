package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.Error.ApiError;
import com.paypal.bfs.test.employeeserv.InputSanitizer.Sanitizer;
import com.paypal.bfs.test.employeeserv.InputValidator.EmployeeValidator;
import com.paypal.bfs.test.employeeserv.InputValidator.EmployeeValidatorResponse;
import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeValidator employeeValidator;

    @Autowired
    private Sanitizer<Employee> sanitizer;

    Logger logger = Logger.getLogger("EmployeeResourceImpl");

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        try {
            id = id.trim();
            Employee employee = employeeDao.employeeGetById(id);
            return ResponseEntity.status(HttpStatus.OK).body(employee);
        } catch (DataAccessException dataAccessException) {
            logger.log(Level.SEVERE, String.format("For id : %s encountered exception %s", id, dataAccessException.getMessage()));
            return buildErrorResponse(HttpStatus.NOT_FOUND, dataAccessException.getMessage());
        } catch (Exception ex) {
            logger.log(Level.SEVERE, String.format("For id : %s encountered exception %s", id, ex.getMessage()));
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    public ResponseEntity createEmployee(final Employee employee) {
        try {
            Employee sanitizeEmployee = sanitizer.sanitize(employee);
            EmployeeValidatorResponse employeeValidatorResponse = employeeValidator.isValid(sanitizeEmployee);
            if(!employeeValidatorResponse.isValid()) {
                logger.log(Level.WARNING, String.format("For employee : %s validations failed.", employee.toString()));
                return buildErrorResponse(HttpStatus.BAD_REQUEST, employeeValidatorResponse.getErrorDetail());
            }
            employeeDao.createEmployee(sanitizeEmployee);
            return ResponseEntity.ok(sanitizeEmployee);
        } catch (DataAccessException dataAccessException) {
            logger.log(Level.SEVERE, String.format("For employee : %s encountered exception %s", employee.toString(), dataAccessException.getMessage()));
            return buildErrorResponse(HttpStatus.BAD_REQUEST, dataAccessException.getMessage());
        } catch (Exception exception) {
            logger.log(Level.SEVERE, String.format("For employee : %s encountered exception %s", employee.toString(), exception.getMessage()));
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    private ResponseEntity buildErrorResponse(HttpStatus httpStatus, String message) {
        ApiError apiError = new ApiError(httpStatus, message);
        return new ResponseEntity(apiError, new HttpHeaders(), apiError.getStatus());
    }

}