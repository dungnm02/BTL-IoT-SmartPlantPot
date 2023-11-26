package dungnm243.webserver.services;

import dungnm243.webserver.models.Pot;
import dungnm243.webserver.models.User;
import dungnm243.webserver.repos.PotRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotService {
    private final PotRepo potRepo;

    public PotService(PotRepo potRepo) {
        this.potRepo = potRepo;
    }

    public Pot getPotById(long id) {
        return potRepo.findById(id).orElse(null);
    }

    public List<Pot> getPotsByUser(User user) {
        return potRepo.findByUser(user);
    }

    public List<Pot> getPotsWithoutUser() {
        return potRepo.findByUserIsNull();
    }

    public boolean addPotToUser(long id, User user) {
        Pot pot = potRepo.findById(id).orElse(null);
        if (pot != null) {
            pot.setUser(user);
            potRepo.saveAndFlush(pot);
            return true;
        } else {
            return false;
        }
    }

    public Pot savePot(Pot pot) {
        return potRepo.saveAndFlush(pot);
    }
}
