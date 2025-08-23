package org.example.killhimka.controller.employee;

import lombok.RequiredArgsConstructor;
import org.example.killhimka.dto.employee.EmployeeCreateDto;
import org.example.killhimka.dto.employee.EmployeeDto;
import org.example.killhimka.service.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<EmployeeDto> addEmployee (@RequestBody EmployeeCreateDto employeeCreateDto){
        EmployeeDto employeeDto = employeeService.addEmployee(employeeCreateDto);
        return new ResponseEntity<>(employeeDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees(); // Предполагается, что есть такой метод в сервисе
        return ResponseEntity.ok(employees);
    }

    // GET-запрос для получения сотрудника по ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(id); // Предполагается, что есть такой метод
        if (employeeDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(employeeDto);
    }

    // PUT-запрос для обновления сотрудника
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeCreateDto employeeCreateDto) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeCreateDto); // Предполагается, что есть такой метод
        if (updatedEmployee == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEmployee);
    }

    // DELETE-запрос для удаления сотрудника
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        boolean deleted = employeeService.deleteEmployee(id); // Предполагается, что есть такой метод
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build(); // 204 No Content - стандарт для успешного удаления
    }
}
