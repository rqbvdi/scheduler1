package com.example.scheduler.service;

import com.example.scheduler.model.Shift;
import com.example.scheduler.model.ShiftDTO;
import com.example.scheduler.model.ShiftEmployeeDTO;
import com.example.scheduler.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShiftService {
    private final ShiftRepository shiftRepository;

    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    public Optional<Shift> getShiftById(Long id) {
        return shiftRepository.findById(id);
    }

    public Shift createShift(Shift shift) {
        return shiftRepository.save(shift);
    }

    public Shift updateShift(Long id, Shift updated) {
        return shiftRepository.findById(id)
                .map(s -> {
                    s.setStartDateTime(updated.getStartDateTime());
                    s.setEndDateTime(updated.getEndDateTime());
                    return shiftRepository.save(s);
                })
                .orElseThrow(() -> new RuntimeException("Shift not found"));
    }

    public void deleteShift(Long id) {
        shiftRepository.deleteById(id);
    }
    public ShiftDTO toDTO(Shift shift) {
        ShiftDTO dto = new ShiftDTO();
        dto.setId(shift.getId());
        dto.setStartDateTime(shift.getStartDateTime());
        dto.setEndDateTime(shift.getEndDateTime());

        List<ShiftEmployeeDTO> employees = shift.getAssignments().stream()
                .map(a -> {
                    ShiftEmployeeDTO e = new ShiftEmployeeDTO();
                    e.setEmployeeId(a.getEmployee().getId());
                    e.setName(a.getEmployee().getName());
                    e.setEmail(a.getEmployee().getEmail());
                    return e;
                })
                .collect(Collectors.toList());

        dto.setEmployees(employees);
        return dto;
    }
}
