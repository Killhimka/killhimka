package org.example.killhimka.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateDto {

    private String name;
    private Integer age;
    private String address;
    private String position;
}
