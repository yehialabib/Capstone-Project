package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class WebController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "index";
    }

@PostMapping("/addEmployee")
@ResponseBody
public String addEmployee(@RequestParam String name) {
    employeeRepository.save(new Employee(name));
    return "Success";
}

@PostMapping("/addTask")
@ResponseBody
public String addTask(@RequestParam String employeeId, @RequestParam String taskDesc) {
    Employee emp = employeeRepository.findById(employeeId).orElseThrow();
    if (emp.getTasks() == null) emp.setTasks(new ArrayList<>());
    
    if (taskDesc != null && !taskDesc.trim().isEmpty()) {
        emp.getTasks().add(taskDesc);
        employeeRepository.save(emp);
    }
    return "Success";
}
}