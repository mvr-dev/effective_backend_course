package dev.effective.lab2;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    List<Employee> all(){
        return repository.findAll();
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee employee){
        return repository.save(employee);
    }

    @GetMapping("/employee/{id}")
    Employee one(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @GetMapping("")
}
