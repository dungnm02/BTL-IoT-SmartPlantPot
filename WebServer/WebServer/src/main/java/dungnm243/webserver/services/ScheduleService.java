package dungnm243.webserver.services;

import dungnm243.webserver.models.Schedule;
import dungnm243.webserver.models.User;
import dungnm243.webserver.repos.ScheduleRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepo scheduleRepo;

    public ScheduleService(ScheduleRepo scheduleRepo) {
        this.scheduleRepo = scheduleRepo;
    }

    public List<Schedule> getSchedulesByUser(User user) {
        return scheduleRepo.findByUser(user);
    }

    public Schedule getScheduleById(Long id) {
        return scheduleRepo.findById(id).orElse(null);
    }

    public Schedule updateSchedule(Schedule schedule) {
        return scheduleRepo.saveAndFlush(schedule);
    }

}
