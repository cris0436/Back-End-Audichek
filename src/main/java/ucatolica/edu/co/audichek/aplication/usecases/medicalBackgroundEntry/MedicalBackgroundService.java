package ucatolica.edu.co.audichek.aplication.usecases.medicalBackgroundEntry;

import ucatolica.edu.co.audichek.exceptions.NotFoundException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalBackgroundEntry;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.MedicalBackgroundEntryDTO;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.MedicalBackgroundMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.MedicalBackgroundEntryRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PatientRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



public class MedicalBackgroundService {

    private final MedicalBackgroundEntryRepository entryRepository;
    private final PatientRepository patientRepository;
    private final ValidateMedicalBackgroundEntry validateMedicalBackgroundEntry;

    public MedicalBackgroundService(
      MedicalBackgroundEntryRepository medicalBackgroundEntryRepository,
      PatientRepository patientRepository,
      ValidateMedicalBackgroundEntry validateMedicalBackgroundEntry
    ){
        this.entryRepository = medicalBackgroundEntryRepository;
        this.patientRepository = patientRepository;
        this.validateMedicalBackgroundEntry = validateMedicalBackgroundEntry;
    }

    public MedicalBackgroundEntryDTO addMedicalBackground(UUID patientId, String description) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundException("Paciente no encontrado"));

        MedicalBackgroundEntry entry = new MedicalBackgroundEntry();
        entry.setPatient(patient);
        entry.setDescription(description);
        entry.setCreatedAt(LocalDateTime.now());

        validateMedicalBackgroundEntry.validateField(entry);

        return MedicalBackgroundMapper.toDTO(entryRepository.save(entry));
    }

    public List<MedicalBackgroundEntryDTO> getMedicalBackgroundByPatient(UUID patientId) {
        MedicalBackgroundEntry temp = new MedicalBackgroundEntry();
        Patient patient = new Patient();
        patient.setId(patientId);
        temp.setPatient(patient);
        temp.setDescription("Validación temporal"); // Dummy data para cumplir interfaz

        validateMedicalBackgroundEntry.validateField(temp);

        return entryRepository.findByPatientId(patientId).stream()
                .map(MedicalBackgroundMapper::toDTO)
                .collect(Collectors.toList());
    }
}