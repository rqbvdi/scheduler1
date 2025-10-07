package com.example.scheduler.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeShiftDTO {
    private Long shiftId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
