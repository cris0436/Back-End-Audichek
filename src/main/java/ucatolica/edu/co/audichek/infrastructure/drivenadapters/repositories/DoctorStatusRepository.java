
package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorStatus;

@Repository
public interface DoctorStatusRepository extends JpaRepository<DoctorStatus, Integer> {

}
