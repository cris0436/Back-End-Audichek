package ucatolica.edu.co.audichek.aplication.usecases.appointment;

import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.exceptions.NotFoundException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Patient;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorAvailabilityRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PatientRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentRequestDTO;
import ucatolica.edu.co.audichek.utils.FieldValidation;

import java.time.LocalDate;
import java.util.Optional;


public class AppointmentValidationUseCases implements FieldValidation<AppointmentRequestDTO> {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentValidationUseCases(
            DoctorAvailabilityRepository doctorAvailabilityRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public Optional<DoctorAvailability> doctorAvailabilityValidation(Appointment appointment, Boolean isAvailable) {
        // Validar parámetros de entrada
        if (appointment == null) {
            throw new BadRequestException("Appointment cannot be null");
        }
        if (isAvailable == null) {
            throw new BadRequestException("Availability status cannot be null");
        }

        // Buscar disponibilidad del doctor asociada
        return Optional.ofNullable(doctorAvailabilityRepository
                .findByDateAvailabilityAndStartTimeAndEndTimeAndDoctorAndIsAvailable(
                        appointment.getDateAppointment(),
                        appointment.getStartTime(),
                        appointment.getEndTime(),
                        appointment.getDoctor(),
                        isAvailable
                ).orElseThrow(() -> new NotFoundException("Doctor availability not found")));
    }

    public void existingValidate(Appointment appointment) {
        if (appointment.getStartTime().isAfter(appointment.getEndTime()) ||
                appointment.getStartTime() == appointment.getEndTime() ) {
            throw new BadRequestException("start time must be before end time");
        }
        if (appointment.getDateAppointment().isBefore(LocalDate.now())) {
            throw new BadRequestException("Date must be after today");
        }

        Optional<Doctor> doctor = doctorRepository.findById(appointment.getDoctor().getId());
        if (doctor.isEmpty()) {
            throw new NotFoundException("Doctor does not exist with the provided ID.");
        }

        // Verify if patient exists by ID
        Optional<Patient> patient = patientRepository.findById(appointment.getPatient().getId());
        if (patient.isEmpty()) {
            throw new NotFoundException("Patient does not exist with the provided ID.");
        }
    }

    @Override
    public void validateField(AppointmentRequestDTO appointmentDTO){
        validateLocalTime(appointmentDTO.getStartTime());
        validateLocalTime(appointmentDTO.getEndTime());
        validateLocalDate(appointmentDTO.getDateAvailability());
        validateUUID(appointmentDTO.getDoctorId());
        validateUUID(appointmentDTO.getPatientId());

        if (appointmentDTO.getDescription() == null
                || appointmentDTO.getDescription().isEmpty()
                || appointmentDTO.getDateAvailability() == null
                || appointmentDTO.getStartTime() == null
                || appointmentDTO.getEndTime() == null
                || appointmentDTO.getDoctorId() == null
                || appointmentDTO.getPatientId() == null) {
            throw new BadRequestException("some fields are missing");
        }



    }


}
