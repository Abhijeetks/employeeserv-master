package com.paypal.bfs.test.employeeserv;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;

public class UserTestUtils {

    public static Employee getEmployee() {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("First");
        employee.setLastName("Last");
        employee.setDateOfBirth("2000-01-01");
        Address address = new Address();
        address.setLine1("line1");
        address.setLine2("line2");
        address.setCity("city");
        address.setState("state");
        address.setCountry("country");
        address.setZipCode("zipcode");
        employee.setAddress(address);
        return employee;
    }
}
