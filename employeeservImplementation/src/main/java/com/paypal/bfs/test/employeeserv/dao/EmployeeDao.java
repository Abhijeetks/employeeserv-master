package com.paypal.bfs.test.employeeserv.dao;

import com.paypal.bfs.test.employeeserv.api.model.Employee;

/*
 Dao Interface
 */
public interface EmployeeDao {

    /**
     * Checks count of employee with the same first name, last name and dateOfBirth
     * @param employee
     * @return count of employees
     */
    public boolean isNewEmployee(Employee employee);

    /**
     * Insert employee details.
     * @param employee
     * @return Employee
     */
    public Employee createEmployee(final Employee employee);


    /**
     * Returns employee details given Id.
     * @param id
     * @return Employee
     */
    public Employee employeeGetById(String id);



}
