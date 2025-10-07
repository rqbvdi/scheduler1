package com.example.scheduler.model;

import lombok.Data;

@Data
public class ShiftAssignmentRequest {
    private Long employeeId;
    private Long shiftId;
}
