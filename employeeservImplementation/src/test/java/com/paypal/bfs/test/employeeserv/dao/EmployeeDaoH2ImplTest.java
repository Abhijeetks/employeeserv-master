package com.paypal.bfs.test.employeeserv.dao;

import com.paypal.bfs.test.employeeserv.UserTestUtils;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeDaoH2ImplTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    EmployeeDaoH2Impl employeeDaoH2Impl = new EmployeeDaoH2Impl();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_EmployeeIsNotNew() {
        Employee employee = UserTestUtils.getEmployee();
        when(jdbcTemplate.queryForObject(Mockito.any(String.class), Mockito.any(Object[].class), eq(Integer.class))).thenReturn(1);
        boolean notNewEmployee = employeeDaoH2Impl.isNewEmployee(employee);
        Assert.assertTrue(notNewEmployee == false);
        verify(jdbcTemplate, times(1)).queryForObject(Mockito.any(String.class), Mockito.any(Object[].class), eq(Integer.class));
    }

    @Test
    public void test_EmployeeGetById() {
        Employee employee = UserTestUtils.getEmployee();
        when(jdbcTemplate.queryForObject(Mockito.any(String.class), Mockito.any(Object[].class), Mockito.any(RowMapper.class))).thenReturn(employee);;
        Employee employeeDao = employeeDaoH2Impl.employeeGetById("1");
        verify(jdbcTemplate, times(1)).queryForObject(Mockito.any(String.class), Mockito.any(Object[].class), Mockito.any(RowMapper.class));
    }


}
