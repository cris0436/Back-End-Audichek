package ucatolica.edu.co.audichek;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.Doctor;

@SpringBootTest
class AudichekApplicationTests {
	@Test
	void contextLoads() {
		Doctor doctorMock = Mockito.mock(Doctor.class);


	}

}
