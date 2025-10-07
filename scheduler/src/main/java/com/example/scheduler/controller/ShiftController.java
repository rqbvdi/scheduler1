package com.example.scheduler.controller;

import com.example.scheduler.model.Shift;
import com.example.scheduler.model.ShiftDTO;
import com.example.scheduler.service.ShiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shifts")
public class ShiftController {
    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping
    public List<ShiftDTO> getAllShifts() {
        return shiftService.getAllShifts().stream()
                .map(shiftService::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftDTO> getShift(@PathVariable Long id) {
        return shiftService.getShiftById(id)
                .map(shiftService::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Shift createShift(@RequestBody Shift shift) {
        return shiftService.createShift(shift);
    }

    @PutMapping("/{id}")
    public Shift updateShift(@PathVariable Long id, @RequestBody Shift shift) {
        return shiftService.updateShift(id, shift);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        shiftService.deleteShift(id);
        return ResponseEntity.noContent().build();
    }
}
