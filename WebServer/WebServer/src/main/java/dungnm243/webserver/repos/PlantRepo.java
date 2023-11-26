package dungnm243.webserver.repos;

import dungnm243.webserver.models.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepo extends JpaRepository<Plant, Long> {
}
