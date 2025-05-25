package ucatolica.edu.co.audichek.aplication.usecases.medicalConsultation;

import org.springframework.transaction.annotation.Transactional;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.MedicalConsultation;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.*;

import org.springframework.stereotype.Service;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.MedicalConsultationDTO;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddMedicalConsultationUseCase {

    private final FieldConsultationValidation fieldValidation;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicalConsultationRepository medicalConsultationRepository;
    private final StatusMedicalConsultationUseCase statusMedicalConsultationUseCase;

    public AddMedicalConsultationUseCase(FieldConsultationValidation fieldValidation,
                                         PatientRepository patientRepository,
                                         DoctorRepository doctorRepository,
                                         MedicalConsultationRepository medicalConsultationRepository,
                                         StatusMedicalConsultationUseCase statusMedicalConsultationUseCase) {
        this.fieldValidation = fieldValidation;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.medicalConsultationRepository = medicalConsultationRepository;
        this.statusMedicalConsultationUseCase = statusMedicalConsultationUseCase;
    }

    @Transactional
    public MedicalConsultation addMedialConsultation(MedicalConsultationDTO medicalConsultationDTO){
        fieldValidation.validateField(medicalConsultationDTO);
        medicalConsultationDTO.setInstructions(fieldValidation.sanitize(medicalConsultationDTO.getInstructions()));

        fieldValidation.validateUUID(medicalConsultationDTO.getPatientId());
        fieldValidation.validateUUID(medicalConsultationDTO.getDoctorId());


        Optional<Patient> patient = patientRepository.getPatientById(
                UUID.fromString(medicalConsultationDTO.getPatientId()));
        if (patient.isEmpty()){
            throw new BadRequestException("Patient not found");
        }
        Optional<Doctor> doctor = doctorRepository.findDoctorById(
                 UUID.fromString(medicalConsultationDTO.getDoctorId()));
        if (doctor.isEmpty()){
            throw new BadRequestException("Doctor not found");
        }
        MedicalConsultation medicalConsultation = medicalConsultationRepository.save(
                new MedicalConsultation(
                        doctor.get(),
                        medicalConsultationDTO.getInstructions(),
                        fieldValidation.convertStringToLocalDateTimeAndBack(medicalConsultationDTO.getConsultationDate()),
                        patient.get()
                )
        );
        statusMedicalConsultationUseCase.createMedicalConsultationStatusHistory(medicalConsultation);

        return medicalConsultation;


    }
}
