package com.example.scheduler.model;

import lombok.Data;

import java.util.List;

@Data
public class EmployeeDTO {
    private Long id;
    private String name;
    private String email;
    private List<EmployeeShiftDTO> shifts;
}
