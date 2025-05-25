package ucatolica.edu.co.audichek.aplication.usecases.appointment;

import jakarta.transaction.Transactional;
import ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities.GetAppointmentType;
import ucatolica.edu.co.audichek.aplication.usecases.appointmentTypesSpecialities.ManageSpecialitiesAppointmentTypes;
import ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability.GetAvailability;
import ucatolica.edu.co.audichek.exceptions.BadRequestException;
import ucatolica.edu.co.audichek.exceptions.NotFoundException;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Appointment;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.AppointmentStatusHistory;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorAvailability;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.mappers.AppointmentMapper;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.AppointmentRepository;
import ucatolica.edu.co.audichek.aplication.usecases.doctorAvailability.DoctorAvailabilityUseCases;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.DoctorRepository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories.PatientRepository;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentRequestDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.AppointmentResponseDTO;
import ucatolica.edu.co.audichek.infrastructure.entrypoints.controllers.dto.DoctorAvailabilityDTO;

import java.util.List;
import java.util.Optional;


public class AddAppointmentUseCases {

    private final AppointmentStatusUseCases appointmentStatusUseCases;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorAvailabilityUseCases doctorAvailabilityUseCases;
    private final PatientRepository patientRepository;
    private final AppointmentValidationUseCases appointmentValidationUseCases;
    private final AppointmentMapper mapper;
    private final GetAvailability getAvailability;
    private final GetAppointmentType getAppointmentType;

    public AddAppointmentUseCases(AppointmentStatusUseCases appointmentStatusUseCases,
                                  AppointmentRepository appointmentRepository,
                                  DoctorAvailabilityUseCases doctorAvailabilityUseCases,
                                  PatientRepository patientRepository,
                                  AppointmentValidationUseCases appointmentValidationUseCases,
                                  AppointmentMapper mapper,
                                  DoctorRepository doctorRepository,
                                  GetAvailability getAvailability,
                                  GetAppointmentType getAppointmentType) {
        this.appointmentStatusUseCases = appointmentStatusUseCases;
        this.appointmentRepository = appointmentRepository;
        this.doctorAvailabilityUseCases = doctorAvailabilityUseCases;
        this.patientRepository = patientRepository;
        this.appointmentValidationUseCases = appointmentValidationUseCases;
        this.mapper = mapper;
        this.doctorRepository = doctorRepository;
        this.getAvailability = getAvailability;
        this.getAppointmentType = getAppointmentType;
    }

    @Transactional
    public AppointmentResponseDTO addAppointment(AppointmentRequestDTO appointmentDTO) {
        // validation
        appointmentDTO.setDescription(appointmentValidationUseCases.sanitize(appointmentDTO.getDescription()));
        appointmentValidationUseCases.validateField(appointmentDTO);
        Appointment appointment = mapper.toEntity(appointmentDTO);
        appointmentValidationUseCases.existingValidate(appointment);
        //validation type appo
        if(!ManageSpecialitiesAppointmentTypes.isValidTypeAppointment(appointmentDTO.getType())){
            throw new NotFoundException("Invalid type of appointment selected any of that listed: \n"+
                    "CONSULTA_AUDIOMETRICA, PRUEBA_AUDIOMETRIA, TERAPIA_AUDITIVA, ADAPTACION_PROTESIS_AUDITIVA, EXAMEN_IMPEDANCIOMETRIA, CONSULTA_OTORRINO, CONTROL_MEDICO_GENERAL ");
        }
        getAppointmentType.validateSpecialityAppointmentType(appointment.getAppointmentType().getDescription());
        appointment.setAppointmentType(getAppointmentType.getAppointmentType(appointment.getAppointmentType().getDescription()));
        getAppointmentType.validateSpecialityAppointmentType(appointment.getAppointmentType().getDescription());

        // Check Type Appointment with doctor speciality

        List<DoctorAvailabilityDTO> typesAppointment = getAvailability.getDoctorsAvailabilityByAppoitmentType(appointmentDTO.getType().toUpperCase());
        typesAppointment = typesAppointment.stream()
                .filter(app -> app.getDoctorId().equals(appointment.getDoctor().getId().toString()))
                .filter(app->app.getStartTime().equals(appointment.getStartTime().toString()) )
                .filter(app->app.getEndTime().equals(appointment.getEndTime().toString()) )
                .filter(app->app.getDateAvailability().equals(appointment.getDateAppointment().toString()) )
                .toList();

        if (typesAppointment.isEmpty() ) {
            throw new BadRequestException("Este horario ya ha sido reservado. Por favor, elige otra opción tipo de cita horario o doctor");
        }

        // Check if the specified date and time are available
        List<Appointment> appointmentsConfirmed = appointmentStatusUseCases.getAppointmentConfirmed(appointment);


        boolean hasActive = appointmentsConfirmed.stream().anyMatch(app -> {
            List<AppointmentStatusHistory> history = appointmentStatusUseCases.getAppointmentHistoryByAppointmentActivate(app);
            return !history.isEmpty() &&
                    !AppointmentStatuses.CANCELLED.toString().equalsIgnoreCase(history.getFirst().getAppointmentStatus().getDescripcion()) &&
                    !AppointmentStatuses.REPROGRAMMED.toString().equalsIgnoreCase(history.getFirst().getAppointmentStatus().getDescripcion());
        });

        if (hasActive) {
            throw new BadRequestException("appointment in same date and time");
        }


        Optional<DoctorAvailability> availability =
                appointmentValidationUseCases.doctorAvailabilityValidation(appointment,true);

        doctorAvailabilityUseCases.updateAvailability(availability.get().getId(), false);
        // Save the appointment if all validations pass
        Appointment newAppointment = appointmentRepository.save(appointment);
        Doctor doctor = doctorRepository.findDoctorById(newAppointment.getDoctor().getId()).orElseThrow(()->new NotFoundException("Doctor not found"));

        appointmentStatusUseCases.createAppointmentStatusHistory(newAppointment,AppointmentStatuses.PENDING.toString());
        System.out.println("Appointment created with id: " + newAppointment.getId() + " and doctor: " + doctor.getPerson().getName());
        return mapper.toDTO(newAppointment);

    }
}
