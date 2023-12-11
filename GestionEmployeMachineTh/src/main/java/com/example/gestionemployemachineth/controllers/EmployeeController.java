package com.example.gestionemployemachineth.controllers;

import com.example.gestionemployemachineth.entities.Employee;
import com.example.gestionemployemachineth.entities.Machine;
import com.example.gestionemployemachineth.repositories.EmployeeRepository;
import com.example.gestionemployemachineth.repositories.MachineRepositry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MachineRepositry machineRepository;

    //private MachineRepositry machineRepository;

    @GetMapping("/employee")
    public String showSignUpForm(Employee employee) {
        return "add-employee";
    }

    @PostMapping("/addemployee")
    public String addEmploye( Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-employee";
        }
        employeeRepository.save(employee);
        model.addAttribute("employees", employeeRepository.findAll());
        return "index";
    }


    @GetMapping("/edit/employee/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "update-emplyee";
    }

    @PostMapping("/update/employee/{id}")
    public String updateEmploye(@PathVariable("id") Long id, Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            employee.setId(id);
            return "update-emplyee";
        }

        employeeRepository.save(employee);
        model.addAttribute("employees", employeeRepository.findAll());
        return "index";
    }

    @GetMapping("/delete/employee/{id}")
    public String deleteEmploye(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id:" + id));
        employeeRepository.delete(employee);
        model.addAttribute("employees", employeeRepository.findAll());
        return "index";
    }
    
//    @GetMapping("/employee/{id}/machines")
//    public String showEmployeeMachines(@PathVariable("id") Long id, Model model) {
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id:" + id));
//      
//        List<Machine> employeeMachines =machineRepository.findByEmployeid(employee);
//
//        model.addAttribute("employee", employee);
//        model.addAttribute("machines", employeeMachines);
//
//        return "filtrage";
//    }
    @GetMapping("/machines/filter")
    public String filterMachinesByEmployee(Model model) {
        List<Employee> allEmployees = employeeRepository.findAll();
        model.addAttribute("employees", allEmployees);
        return "filter-machines";
    }

    @GetMapping("/machines/filter/result")
    public String showFilteredMachines(@RequestParam("employeeId") Long employeeId, Model model) {
        Employee selectedEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee Id:" + employeeId));

        List<Machine> employeeMachines = machineRepository.findByEmployeid(selectedEmployee);
        model.addAttribute("machines", employeeMachines);
        return "filtred-machines";
    }
}