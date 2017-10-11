package service;

import entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class EmployeeController {

    @RequestMapping("/employees")
    public List<Employee> getEmployees(){
        return Collections.emptyList();
    }
}
