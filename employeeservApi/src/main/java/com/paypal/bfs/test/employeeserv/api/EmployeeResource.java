package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Interface for employee resource operations.
 */
public interface EmployeeResource {

    /**
     * Retrieves the {@link Employee} resource by id.
     *
     * @param id employee id.
     * @return {@link Employee} resource.
     */
    @RequestMapping("/v1/bfs/employees/{id}")
    ResponseEntity<Employee> employeeGetById(@PathVariable("id") String id);

    /**
     * HTTP Post request to create employee.
     * @param employee
     * @return ResponseEntity
     */
    @PostMapping(value = "/v1/bfs/employee", consumes = APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity createEmployee(@Valid @RequestBody final Employee employee);
}
