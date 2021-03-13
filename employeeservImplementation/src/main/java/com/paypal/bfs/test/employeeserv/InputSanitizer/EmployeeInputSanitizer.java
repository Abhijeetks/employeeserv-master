package com.paypal.bfs.test.employeeserv.InputSanitizer;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Sanitizes the employee input
 */
@Component
public class EmployeeInputSanitizer implements Sanitizer<Employee> {
    /**
     * Sanitizes input request
     * @param employee
     * @return Employee
     */
    public Employee sanitize(Employee employee) {
        Employee sanitizedEmployee = new Employee();
        sanitizedEmployee.setFirstName(sanitizeField(employee.getFirstName()));
        sanitizedEmployee.setLastName(sanitizeField(employee.getLastName()));
        sanitizedEmployee.setDateOfBirth(sanitizeField(employee.getDateOfBirth()));
        sanitizedEmployee.setAddress(sanitizeAddress(employee));
        return sanitizedEmployee;
    }

    private Address sanitizeAddress(Employee employee) {
        Address address = new Address();
        address.setLine1(sanitizeField(employee.getAddress().getLine1()));
        address.setLine2(sanitizeField(employee.getAddress().getLine2()));
        address.setCity(sanitizeField(employee.getAddress().getCity()));
        address.setState(sanitizeField(employee.getAddress().getState()));
        address.setCountry(sanitizeField(employee.getAddress().getCountry()));
        address.setZipCode(sanitizeField(employee.getAddress().getZipCode()));
        return address;
    }

    private String sanitizeField(String field) {
        return StringUtils.isEmpty(field) ? StringUtils.EMPTY : field.trim();
    }
}
