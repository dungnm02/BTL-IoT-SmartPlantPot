package dungnm243.webserver.repos;

import dungnm243.webserver.models.PotStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotStatisticRepo extends JpaRepository<PotStatistic, Long> {

}
