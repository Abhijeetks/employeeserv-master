package com.paypal.bfs.test.employeeserv.dao;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Employee Dao object which interacts with H2 DB.
 */
@Repository
public class EmployeeDaoH2Impl implements EmployeeDao {

    public final static String SELECT_EMPLOYEE_COUNT_QUERY = "SELECT count(*) as COUNT FROM employee WHERE first_name = ? AND last_name = ? AND date_of_birth = ?";
    public final static String INSERT_EMPLOYEE_QUERY = "INSERT INTO employee (first_name, last_name, date_of_birth) VALUES  (?, ?, ?)";
    public final static String INSERT_ADDRESS_QUERY = "INSERT INTO address (employee_id, line1, line2, city, state, country, zip_code) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public final static String SELECT_EMPLOYEE_QUERY = "SELECT employee.*, address.* FROM employee JOIN address ON employee.id = address.employee_id WHERE id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Logger logger = Logger.getLogger("EmployeeDao");

    /**
     * Checks count of employee with the same first name, last name and dateOfBirth
     * @param employee
     * @return count of employees
     */
    @Override
    public boolean isNewEmployee(Employee employee) {
        int employeeCount = jdbcTemplate.queryForObject(SELECT_EMPLOYEE_COUNT_QUERY,
                        new Object[]{employee.getFirstName(), employee.getLastName(), employee.getDateOfBirth()}, Integer.class);
        return employeeCount == 0;
    }



    /**
     * Insert employee details.
     * @param employee
     * @return Employee
     */
    @Override
    @Transactional
    public Employee createEmployee(final Employee employee) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            //Insert Employee
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_QUERY, new String[]{"id"});
                            preparedStatement.setString(1, employee.getFirstName());
                            preparedStatement.setString(2, employee.getLastName());
                            preparedStatement.setString(3, employee.getDateOfBirth());
                            return preparedStatement;
                        }
                    },
                    keyHolder);
            //Insert Address.
            jdbcTemplate.update(INSERT_ADDRESS_QUERY,
                    keyHolder.getKey(),
                    /** Address params **/
                    employee.getAddress().getLine1(), employee.getAddress().getLine2(),
                    employee.getAddress().getCity(), employee.getAddress().getState(),
                    employee.getAddress().getCountry(),
                    employee.getAddress().getZipCode());
            employee.setId(keyHolder.getKey().intValue());
            logger.log(Level.INFO, "Successfully created employee.");
            return employee;
    }

    /**
     * Returns employee details given Id.
     * @param id
     * @return Employee
     */
    @Override
    public Employee employeeGetById(String id) {
        Employee employee = null;
        try {
            employee = jdbcTemplate.queryForObject(SELECT_EMPLOYEE_QUERY, new Object[]{id},
                    (resultSet, i) -> {
                        Employee emp = new Employee();
                        Address address = new Address();
                        emp.setFirstName(resultSet.getString("first_name"));
                        emp.setLastName(resultSet.getString("last_name"));
                        emp.setDateOfBirth(resultSet.getDate("date_of_birth").toString());
                        address.setLine1(resultSet.getString("line1"));
                        address.setLine2(resultSet.getString("line2"));
                        address.setCity(resultSet.getString("city"));
                        address.setState(resultSet.getString("state"));
                        address.setCountry(resultSet.getString("country"));
                        address.setZipCode(resultSet.getString("zip_code"));
                        emp.setAddress(address);
                        return emp;
                    });
        } catch (EmptyResultDataAccessException e) {
            logger.log(Level.WARNING, String.format("No employee with id : %s", id));
            throw e;
        }
        return employee;
    }
}
