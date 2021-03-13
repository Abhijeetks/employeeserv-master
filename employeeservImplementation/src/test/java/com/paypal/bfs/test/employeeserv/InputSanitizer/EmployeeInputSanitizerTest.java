package com.paypal.bfs.test.employeeserv.InputSanitizer;

import com.paypal.bfs.test.employeeserv.UserTestUtils;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.junit.Assert;
import org.junit.Test;

public class EmployeeInputSanitizerTest {

    EmployeeInputSanitizer employeeInputSanitizer = new EmployeeInputSanitizer();

    @Test
    public void test_Sanitize_All() {
        String emptyString = "   ";
        Employee employee = UserTestUtils.getEmployee();
        Address address = employee.getAddress();
        employee.setFirstName(emptyString + employee.getFirstName() + emptyString);
        employee.setLastName(emptyString + employee.getLastName() + emptyString);
        employee.setDateOfBirth(emptyString + employee.getDateOfBirth() + emptyString);
        address.setLine1(emptyString + address.getLine1() + emptyString);
        address.setCity(emptyString + address.getCity() + emptyString);
        address.setState(emptyString + address.getState() + emptyString);
        address.setCountry(emptyString + address.getCountry() + emptyString);
        address.setZipCode(emptyString + address.getZipCode() + emptyString);
        Employee outputEmployee = employeeInputSanitizer.sanitize(employee);
        Assert.assertTrue(employee.getFirstName().trim().equals(outputEmployee.getFirstName()));
        Assert.assertTrue(employee.getLastName().trim().equals(outputEmployee.getLastName()));
        Assert.assertTrue(employee.getDateOfBirth().trim().equals(outputEmployee.getDateOfBirth()));
        Assert.assertTrue(employee.getAddress().getLine1().trim().equals(outputEmployee.getAddress().getLine1()));
        Assert.assertTrue(employee.getAddress().getCity().trim().equals(outputEmployee.getAddress().getCity()));
        Assert.assertTrue(employee.getAddress().getState().trim().equals(outputEmployee.getAddress().getState()));
        Assert.assertTrue(employee.getAddress().getCountry().trim().equals(outputEmployee.getAddress().getCountry()));
        Assert.assertTrue(employee.getAddress().getZipCode().trim().equals(outputEmployee.getAddress().getZipCode()));
    }
}
