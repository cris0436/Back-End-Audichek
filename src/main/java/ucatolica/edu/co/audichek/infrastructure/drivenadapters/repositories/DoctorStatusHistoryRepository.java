
package ucatolica.edu.co.audichek.infrastructure.drivenadapters.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ucatolica.edu.co.audichek.infrastructure.drivenadapters.entities.DoctorStatusHistory;

@Repository
public interface DoctorStatusHistoryRepository extends JpaRepository<DoctorStatusHistory, Integer> {
}