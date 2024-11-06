package com.example.rest;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> all(){
        List<EntityModel<Employee>> employees = repository.findAll().stream()
                .map(employee ->
                    EntityModel.of(employee,
                            linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
                            linkTo(methodOn(EmployeeController.class).all()).withRel("employees"))
                ).collect(Collectors.toList());
        return CollectionModel.of(employees,linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee employee){
        return repository.save(employee);
    }

    @GetMapping("/employees/{id}")
    EntityModel<Employee> one(@PathVariable Long id){
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return EntityModel.of(employee,
                linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
//
    }

    @PutMapping("employees/{id}")
    Employee replaceEmployee(@RequestBody Employee employee, @PathVariable Long id){
        return repository.findById(id)
                .map(employee1 -> {
                    employee1.setName(employee.getName());
                    employee1.setRole(employee.getRole());
                    return repository.save(employee1);
                })
                .orElseGet(() -> {
                    return repository.save(employee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
