package dungnm243.webserver.services;

import dungnm243.webserver.repos.PotStatisticRepo;
import org.springframework.stereotype.Service;

@Service
public class PotStatistic {
    private final PotStatisticRepo potStatisticRepo;

    public PotStatistic(PotStatisticRepo potStatisticRepo) {
        this.potStatisticRepo = potStatisticRepo;
    }


}
