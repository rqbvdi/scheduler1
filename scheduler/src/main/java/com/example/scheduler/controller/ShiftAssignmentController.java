package com.example.scheduler.controller;

import com.example.scheduler.model.ShiftAssignment;
import com.example.scheduler.model.ShiftAssignmentDTO;
import com.example.scheduler.model.ShiftAssignmentRequest;
import com.example.scheduler.service.ShiftAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignments")
public class ShiftAssignmentController {
    private final ShiftAssignmentService assignmentService;

    public ShiftAssignmentController(ShiftAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    // Return all assignments as DTOs
    @GetMapping
    public List<ShiftAssignmentDTO> getAllAssignments() {
        return assignmentService.getAllAssignmentsDTO();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftAssignmentDTO> getAssignment(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id)
                .map(a -> ResponseEntity.ok(assignmentService.toDTO(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ShiftAssignmentDTO createAssignment(@RequestBody ShiftAssignmentRequest request) {
        ShiftAssignment assignment = assignmentService.createAssignment(
                request.getEmployeeId(),
                request.getShiftId()
        );
        return assignmentService.toDTO(assignment);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
