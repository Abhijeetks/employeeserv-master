package com.paypal.bfs.test.employeeserv.InputValidator;

import com.paypal.bfs.test.employeeserv.UserTestUtils;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static com.paypal.bfs.test.employeeserv.InputValidator.EmployeeValidator.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeValidatorTest {

    @Mock
    EmployeeDao employeeDao;

    @InjectMocks
    EmployeeValidator employeeValidator = new EmployeeValidator();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_Employee_NotDuplicate() {
        Employee employee = UserTestUtils.getEmployee();
        when(employeeDao.isNewEmployee(employee)).thenReturn(true);
        EmployeeValidatorResponse employeeValidatorResponse = employeeValidator.isValid(employee);
        verify(employeeDao, times(1)).isNewEmployee(employee);
        Assert.assertTrue(employeeValidatorResponse.isValid());
    }

    @Test
    public void test_Employee_Duplicate() {
        Employee employee = UserTestUtils.getEmployee();
        when(employeeDao.isNewEmployee(employee)).thenReturn(false);
        EmployeeValidatorResponse employeeValidatorResponse = employeeValidator.isValid(employee);
        verify(employeeDao, times(1)).isNewEmployee(employee);
        Assert.assertTrue(!employeeValidatorResponse.isValid());
    }

    @Test
    public void test_EmptyEmployee() {
        Employee employee = new Employee();
        employee.setAddress(new Address());
        when(employeeDao.isNewEmployee(employee)).thenReturn(false);
        EmployeeValidatorResponse response = employeeValidator.isValid(employee);
        verify(employeeDao, times(1)).isNewEmployee(employee);
        Assert.assertTrue(!response.isValid());
        Assert.assertTrue(response.getErrorDetail().contains(FIRST_NAME_EMPTY_MESSAGE));
        Assert.assertTrue(response.getErrorDetail().contains(LAST_NAME_EMPTY_MESSAGE));
        Assert.assertTrue(response.getErrorDetail().contains(DOB_EMPTY_MESSAGE));
        Assert.assertTrue(response.getErrorDetail().contains(LINE1_EMPTY_MESSAGE));
        Assert.assertTrue(response.getErrorDetail().contains(CITY_EMPTY_MESSAGE));
        Assert.assertTrue(response.getErrorDetail().contains(STATE_EMPTY_MESSAGE));
        Assert.assertTrue(response.getErrorDetail().contains(COUNTRY_EMPTY_MESSAGE));
        Assert.assertTrue(response.getErrorDetail().contains(ZIP_CODE_EMPTY_MESSAGE));
    }

    @Test
    public void test_EmployeeDobFormatNotSupported() {
        Employee employee = UserTestUtils.getEmployee();
        employee.setDateOfBirth("----");
        when(employeeDao.isNewEmployee(employee)).thenReturn(true);
        EmployeeValidatorResponse response = employeeValidator.isValid(employee);
        verify(employeeDao, times(1)).isNewEmployee(employee);
        Assert.assertTrue(!response.isValid());
        Assert.assertTrue(response.getErrorDetail().contains(DOB_SUPPORTED_MESSAGE));
    }

    @Test
    public void test_EmployeeAddressEmpty() {
        Employee employee = UserTestUtils.getEmployee();
        employee.setAddress(null);
        when(employeeDao.isNewEmployee(employee)).thenReturn(true);
        EmployeeValidatorResponse response = employeeValidator.isValid(employee);
        verify(employeeDao, times(1)).isNewEmployee(employee);
        Assert.assertTrue(!response.isValid());
        Assert.assertTrue(response.getErrorDetail().contains(ADDRESS_ERROR_MESSAGE));
    }
}
