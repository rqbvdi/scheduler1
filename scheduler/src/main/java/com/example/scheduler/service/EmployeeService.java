package com.example.scheduler.service;

import com.example.scheduler.model.Employee;
import com.example.scheduler.model.EmployeeDTO;
import com.example.scheduler.model.EmployeeShiftDTO;
import com.example.scheduler.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updated) {
        return employeeRepository.findById(id)
                .map(e -> {
                    e.setName(updated.getName());
                    e.setEmail(updated.getEmail());
                    return employeeRepository.save(e);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());

        List<EmployeeShiftDTO> shifts = employee.getAssignments().stream()
                .map(a -> {
                    EmployeeShiftDTO s = new EmployeeShiftDTO();
                    s.setShiftId(a.getShift().getId());
                    s.setStartDateTime(a.getShift().getStartDateTime());
                    s.setEndDateTime(a.getShift().getEndDateTime());
                    return s;
                })
                .collect(Collectors.toList());

        dto.setShifts(shifts);
        return dto;
    }
}
