package dungnm243.webserver.repos;

import dungnm243.webserver.models.LightSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightScheduleRepo extends JpaRepository<LightSchedule, Long> {
}
