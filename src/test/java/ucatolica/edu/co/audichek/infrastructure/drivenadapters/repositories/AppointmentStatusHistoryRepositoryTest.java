package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppointmentStatusHistoryRepositoryTest {

    @Test
    void findByAppointment_DoctorAndIsActiveAndAppointmentStatus_Descripcion() {
    }

    @Test
    void findAppointmentStatusHistoriesByAppointment() {
    }

    @Test
    void findAppointmentStatusHistoriesByAppointmentAndIsActive() {
    }
}