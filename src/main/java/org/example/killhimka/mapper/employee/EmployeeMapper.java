package org.example.killhimka.mapper.employee;

import org.example.killhimka.dto.employee.EmployeeCreateDto;
import org.example.killhimka.dto.employee.EmployeeDto;
import org.example.killhimka.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

    Employee toEntity (EmployeeCreateDto employeeCreateDto);

    @Mapping(target = "id", ignore = true)
    Employee updateEntity (@MappingTarget Employee employee, EmployeeCreateDto employeeCreateDto);

    EmployeeDto toDto (Employee employee);

    List<EmployeeDto> toListDto (List<Employee> employees);
}
