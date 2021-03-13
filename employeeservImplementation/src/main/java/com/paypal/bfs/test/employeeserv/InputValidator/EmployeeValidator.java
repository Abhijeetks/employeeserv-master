package com.paypal.bfs.test.employeeserv.InputValidator;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.dao.EmployeeDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeValidator {

    private final static String DATE_FORMAT = "YYYY-MM-DD";
    public final static String DOB_EMPTY_MESSAGE = "Date of Birth not be empty";
    public final static String DOB_SUPPORTED_MESSAGE = "Date supported format is " + DATE_FORMAT;
    public final static String FIRST_NAME_EMPTY_MESSAGE = "First Name can not be empty";
    public final static String LAST_NAME_EMPTY_MESSAGE = "Last Name can not be empty";
    public final static String LINE1_EMPTY_MESSAGE = "Line1 can not be empty";
    public final static String CITY_EMPTY_MESSAGE = "City can not be empty";
    public final static String STATE_EMPTY_MESSAGE = "State can not be empty";
    public final static String ZIP_CODE_EMPTY_MESSAGE = "ZIP code can not be empty";
    public final static String COUNTRY_EMPTY_MESSAGE = "Country can not be empty";
    public final static String ADDRESS_ERROR_MESSAGE = "Address is mandatory";

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * Check validity of sanitized record.
     * @param employee
     * @return EmployeeValidatorResponse
     */
    public EmployeeValidatorResponse isValid(Employee employee) {
        List<String> errorList = new ArrayList<String>();
        checkDuplicate(employee, errorList);
        checkName(employee, errorList);
        checkDoB(employee, errorList);
        checkAddress(employee, errorList);
        return new EmployeeValidatorResponse(errorList.size() == 0, String.join(", ", errorList));
    }

    private void checkDuplicate(Employee employee, List<String> errorList) {
        boolean isNew = employeeDao.isNewEmployee(employee);
        if(!isNew) errorList.add("Employee already exists");
    }

    private void checkDoB(Employee employee, List<String> errorList) {
        if(StringUtils.isEmpty(employee.getDateOfBirth())) {
            errorList.add(DOB_EMPTY_MESSAGE);
            return;
        }
        if(!isDateValid(employee)) errorList.add(DOB_SUPPORTED_MESSAGE);
    }

    private void checkName(Employee employee, List<String> errorList) {
        if(StringUtils.isEmpty(employee.getFirstName())) errorList.add(FIRST_NAME_EMPTY_MESSAGE);
        if(StringUtils.isEmpty(employee.getLastName())) errorList.add(LAST_NAME_EMPTY_MESSAGE);
    }

    private void checkAddress(Employee employee, List<String> errorList) {
        if(employee.getAddress() == null) {
            errorList.add(ADDRESS_ERROR_MESSAGE);
            return;
        }
        if(StringUtils.isEmpty(employee.getAddress().getLine1())) errorList.add(LINE1_EMPTY_MESSAGE);
        if(StringUtils.isEmpty(employee.getAddress().getCity())) errorList.add(CITY_EMPTY_MESSAGE);
        if(StringUtils.isEmpty(employee.getAddress().getState())) errorList.add(STATE_EMPTY_MESSAGE);
        if(StringUtils.isEmpty(employee.getAddress().getCountry())) errorList.add(COUNTRY_EMPTY_MESSAGE);
        if(StringUtils.isEmpty(employee.getAddress().getZipCode())) errorList.add(ZIP_CODE_EMPTY_MESSAGE);
    }

    private boolean isDateValid(final Employee employee) {
        try {
            new SimpleDateFormat(DATE_FORMAT).parse(employee.getDateOfBirth());
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
