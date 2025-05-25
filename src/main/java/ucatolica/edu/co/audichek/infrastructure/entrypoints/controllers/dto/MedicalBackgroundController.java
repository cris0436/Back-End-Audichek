package ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucatolica.edu.co.audichek.aplication.usecases.medicalBackgroundEntry.MedicalBackgroundService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class MedicalBackgroundController {

    private final MedicalBackgroundService medicalBackgroundService;

    @PostMapping("/{patientId}/background")
    public ResponseEntity<MedicalBackgroundEntryDTO> addMedicalBackground(
            @PathVariable UUID patientId,
            @RequestBody String description) {
        return ResponseEntity.ok(medicalBackgroundService.addMedicalBackground(patientId, description));
    }

    @GetMapping("/{patientId}/background")
    public ResponseEntity<List<MedicalBackgroundEntryDTO>> getMedicalBackground(
            @PathVariable UUID patientId) {
        return ResponseEntity.ok(medicalBackgroundService.getMedicalBackgroundByPatient(patientId));
    }
}
