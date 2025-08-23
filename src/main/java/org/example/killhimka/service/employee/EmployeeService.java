package org.example.killhimka.service.employee;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.killhimka.dto.employee.EmployeeCreateDto;
import org.example.killhimka.dto.employee.EmployeeDto;
import org.example.killhimka.dto.user.UserDto;
import org.example.killhimka.mapper.employee.EmployeeMapper;
import org.example.killhimka.entity.Employee;
import org.example.killhimka.repository.employee.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public EmployeeDto addEmployee (EmployeeCreateDto employeeCreateDto){
        Employee employee = null;

        employee = employeeMapper.toEntity(employeeCreateDto);
        employee = employeeRepository.save(employee);

        EmployeeDto employeeDto = employeeMapper.toDto(employee);

        return employeeDto;
    }

    public List<EmployeeDto> getAllEmployees (){
        List<EmployeeDto> employeeDtos = employeeMapper.toListDto(employeeRepository.findAll());

        return employeeDtos;
    }

    public EmployeeDto getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no such post or it has been deleted."));

        EmployeeDto employeeDto = employeeMapper.toDto(employee);

        return employeeDto;
    }

    public EmployeeDto updateEmployee(Long id, EmployeeCreateDto employeeCreateDto){
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no such post or it has been deleted."));

        Employee updateEmployee = employeeMapper.updateEntity(employee,employeeCreateDto);

        employeeRepository.save(updateEmployee);

        return employeeMapper.toDto(updateEmployee);
    }

    public boolean deleteEmployee(Long id) {
        boolean exists = employeeRepository.existsById(id);
        if (exists) {
            employeeRepository.deleteById(id);
        }
        return exists;
    }
}
