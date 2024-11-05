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

    @GetMapping("/employees/{id}")
    Employee one(@PathVariable Long id){
        return repository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException(id));
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
