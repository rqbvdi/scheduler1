package com.example.scheduler.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ShiftDTO {
    private Long id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<ShiftEmployeeDTO> employees; // renamed from assignments
}
