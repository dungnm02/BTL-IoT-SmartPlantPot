package dungnm243.webserver.repos;

import dungnm243.webserver.models.Pot;
import dungnm243.webserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PotRepo extends JpaRepository<Pot, Long> {
    List<Pot> findByUser(User user);

    List<Pot> findByUserIsNull();
}

