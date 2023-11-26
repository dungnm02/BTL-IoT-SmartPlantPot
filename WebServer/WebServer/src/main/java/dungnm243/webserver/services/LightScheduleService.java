package dungnm243.webserver.services;

import dungnm243.webserver.models.LightSchedule;
import dungnm243.webserver.repos.LightScheduleRepo;
import org.springframework.stereotype.Service;

@Service
public class LightScheduleService {
    private final LightScheduleRepo lightScheduleRepo;

    public LightScheduleService(LightScheduleRepo lightScheduleRepo) {
        this.lightScheduleRepo = lightScheduleRepo;
    }

    public LightSchedule getLightScheduleById(long id) {
        return lightScheduleRepo.findById(id).orElse(null);
    }

    public LightSchedule updateLightSchedule(LightSchedule lightSchedule) {
        return lightScheduleRepo.saveAndFlush(lightSchedule);
    }
}
