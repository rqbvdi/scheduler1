package com.example.scheduler.service;

import com.example.scheduler.model.Employee;
import com.example.scheduler.model.Shift;
import com.example.scheduler.model.ShiftAssignment;
import com.example.scheduler.model.ShiftAssignmentDTO;
import com.example.scheduler.repository.EmployeeRepository;
import com.example.scheduler.repository.ShiftAssignmentRepository;
import com.example.scheduler.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShiftAssignmentService {
    private final ShiftAssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ShiftRepository shiftRepository;

    public ShiftAssignmentService(ShiftAssignmentRepository assignmentRepository,
                                  EmployeeRepository employeeRepository,
                                  ShiftRepository shiftRepository) {
        this.assignmentRepository = assignmentRepository;
        this.employeeRepository = employeeRepository;
        this.shiftRepository = shiftRepository;
    }

    public List<ShiftAssignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public List<ShiftAssignmentDTO> getAllAssignmentsDTO() {
        return assignmentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ShiftAssignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }

    public ShiftAssignment createAssignment(Long employeeId, Long shiftId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        ShiftAssignment assignment = new ShiftAssignment();
        assignment.setEmployee(employee);
        assignment.setShift(shift);

        return assignmentRepository.save(assignment);
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    // Mapper
    public ShiftAssignmentDTO toDTO(ShiftAssignment assignment) {
        ShiftAssignmentDTO dto = new ShiftAssignmentDTO();
        dto.setId(assignment.getId());
        dto.setEmployeeId(assignment.getEmployee().getId());
        dto.setEmployeeName(assignment.getEmployee().getName());
        dto.setShiftId(assignment.getShift().getId());
        dto.setStartDateTime(assignment.getShift().getStartDateTime());
        dto.setEndDateTime(assignment.getShift().getEndDateTime());
        return dto;
    }
}
