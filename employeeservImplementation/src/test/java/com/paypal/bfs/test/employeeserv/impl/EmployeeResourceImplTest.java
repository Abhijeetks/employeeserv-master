package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.InputSanitizer.Sanitizer;
import com.paypal.bfs.test.employeeserv.InputValidator.EmployeeValidator;
import com.paypal.bfs.test.employeeserv.InputValidator.EmployeeValidatorResponse;
import com.paypal.bfs.test.employeeserv.UserTestUtils;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeDao;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeResourceImplTest {

    @Mock
    EmployeeDao employeeDao;

    @Mock
    EmployeeValidator employeeValidator;

    @Mock
    Sanitizer<Employee> sanitizer;

    @InjectMocks
    EmployeeResourceImpl employeeResource = new EmployeeResourceImpl();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_GetById_Success() {
        Employee employee = UserTestUtils.getEmployee();
        when(employeeDao.employeeGetById(String.valueOf(employee.getId()))).thenReturn(employee);
        ResponseEntity<Employee> responseEntity = employeeResource.employeeGetById(String.valueOf(employee.getId()));
        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
        verify(employeeDao, times(1)).employeeGetById(String.valueOf(employee.getId()));
    }

    @Test
    public void test_GetById_Failure() {
        Employee employee = UserTestUtils.getEmployee();
        when(employeeDao.employeeGetById(String.valueOf(employee.getId()))).thenThrow(new DataAccessResourceFailureException("Failure"));
        ResponseEntity<Employee> responseEntity = employeeResource.employeeGetById(String.valueOf(employee.getId()));
        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.NOT_FOUND);
        verify(employeeDao, times(1)).employeeGetById(String.valueOf(employee.getId()));
    }

    @Test
    public void test_CreateEmployee_Success() {
        Employee employee = UserTestUtils.getEmployee();
        EmployeeValidatorResponse employeeValidatorResponse = new EmployeeValidatorResponse(true, StringUtils.EMPTY);
        when(sanitizer.sanitize(employee)).thenReturn(employee);
        when(employeeValidator.isValid(employee)).thenReturn(employeeValidatorResponse);
        when(employeeDao.createEmployee(employee)).thenReturn(employee);
        ResponseEntity<Employee> responseEntity = employeeResource.createEmployee(employee);
        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
        verify(sanitizer, times(1)).sanitize(employee);
        verify(employeeValidator, times(1)).isValid(employee);
        verify(employeeDao, times(1)).createEmployee(employee);
    }

    @Test
    public void test_CreateEmployee_Failure() {
        Employee employee = UserTestUtils.getEmployee();
        when(sanitizer.sanitize(employee)).thenReturn(employee);
        EmployeeValidatorResponse employeeValidatorResponse = new EmployeeValidatorResponse(true, StringUtils.EMPTY);
        when(sanitizer.sanitize(employee)).thenReturn(employee);
        when(employeeValidator.isValid(employee)).thenReturn(employeeValidatorResponse);
        when(employeeDao.createEmployee(employee)).thenThrow(new DataAccessResourceFailureException("Failure"));
        ResponseEntity<Employee> responseEntity = employeeResource.createEmployee(employee);
        Assert.assertTrue(responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST);
        verify(sanitizer, times(1)).sanitize(employee);
        verify(employeeValidator, times(1)).isValid(employee);
        verify(employeeDao, times(1)).createEmployee(employee);
    }

}
