package dungnm243.webserver.repos;

import dungnm243.webserver.models.Plant;
import dungnm243.webserver.models.Schedule;
import dungnm243.webserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUser(User user);

}
