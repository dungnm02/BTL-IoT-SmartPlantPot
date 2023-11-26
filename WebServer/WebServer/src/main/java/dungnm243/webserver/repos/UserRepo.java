package dungnm243.webserver.repos;

import dungnm243.webserver.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public User findByUsername(String username);

    public User findByEmail(String email);
}
