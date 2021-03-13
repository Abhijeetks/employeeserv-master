package FunctionalTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.EmployeeservApplication;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeservApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeResourceFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    public static String convertToJson(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_1UserExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/bfs/employees/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void test_2CreateUser() throws Exception {
        Employee employee = getEmployee();
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/employee").content(convertToJson(employee))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void test_3CreateUserFails() throws Exception {
        Employee employee = getEmployee();
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/bfs/employee").content(convertToJson(employee))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_4UserAvailable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/bfs/employees/4").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }



    public static Employee getEmployee() {
        Employee employee = new Employee();
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