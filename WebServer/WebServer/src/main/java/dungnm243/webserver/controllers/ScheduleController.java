package dungnm243.webserver.controllers;

import dungnm243.webserver.models.*;
import dungnm243.webserver.services.LightScheduleService;
import dungnm243.webserver.services.PlantService;
import dungnm243.webserver.services.PotService;
import dungnm243.webserver.services.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final LightScheduleService lightScheduleService;
    private final PlantService plantService;
    private final PotService potService;

    public ScheduleController(ScheduleService scheduleService, LightScheduleService lightScheduleService, PlantService plantService, PotService potService) {
        this.scheduleService = scheduleService;
        this.lightScheduleService = lightScheduleService;
        this.plantService = plantService;
        this.potService = potService;
    }

    @GetMapping("/schedules")
    public String showScheduleList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Schedule> userSchedules = (List<Schedule>) session.getAttribute("userSchedules");
        if (userSchedules == null) {
            userSchedules = scheduleService.getSchedulesByUser(user);
            session.setAttribute("userSchedules", userSchedules);
        }
        List<Pot> userPots = (List<Pot>) session.getAttribute("userPots");
        if (userPots == null) {
            userPots = potService.getPotsByUser(user);
            session.setAttribute("userPots", userPots);
        }
        model.addAttribute("userSchedules", userSchedules);
        model.addAttribute("userPots", userPots);
        model.addAttribute("userPlants", plantService.getPlantsInPotList(userPots));
        return "schedules";
    }

    @GetMapping("/schedules/{scheduleId}")
    public String showSchedule(@PathVariable Long scheduleId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Schedule schedule = (Schedule) session.getAttribute("schedule");
        if (scheduleId == -1) {
            // Tọa lịch trình mới
            schedule = new Schedule();
        } else if (schedule == null) {
            // Lấy từ CSDL nếu chưa có trong session
            schedule = scheduleService.getScheduleById(scheduleId);
        }
        session.setAttribute("schedule", schedule);
        session.setAttribute("lightSchedules", schedule.getLightSchedules());
        model.addAttribute("schedule", schedule);
        model.addAttribute("lightSchedules", schedule.getLightSchedules());
        return "schedule";
    }

    /***
     * Cập nhật/tạo mới lịch trình
     * @param schedule
     * @return
     */
    @PostMapping("/schedules/{scheduleId}")
    public String updateSchedule(@ModelAttribute Schedule schedule, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        for (LightSchedule ls : (List<LightSchedule>) session.getAttribute("lightSchedules")) {
            lightScheduleService.updateLightSchedule(ls);
        }
        schedule.setLightSchedules((List<LightSchedule>) session.getAttribute("lightSchedules"));
        // Xóa lịch trình cũ trong session
        session.removeAttribute("schedule");
        session.removeAttribute("lightSchedules");
        scheduleService.updateSchedule(schedule);
        return "redirect:/schedules";
    }

    @GetMapping("/schedules/{scheduleId}/lightSchedules/{lightScheduleId}")
    public String showLightSchedule(@PathVariable Long scheduleId, @PathVariable Long lightScheduleId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        LightSchedule lightSchedule = null;
        if (lightScheduleId == 0) {
            // Tạo mới lịch chiếu sáng
            lightSchedule = new LightSchedule();
        } else {
            // Xem lịch chiếu sáng cũ
            for (LightSchedule ls : (List<LightSchedule>) session.getAttribute("lightSchedules")) {
                if (ls.getId() == lightScheduleId) {
                    lightSchedule = ls;
                    break;
                }
            }
        }
        model.addAttribute("lightSchedule", lightSchedule);
        return "lightSchedule";
    }

    @PostMapping("/schedules/{scheduleId}/lightSchedules/{lightScheduleId}")
    public String updateLightSchedule(@PathVariable long scheduleId, @PathVariable long lightScheduleId, @ModelAttribute LightSchedule lightSchedule, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<LightSchedule> lightSchedules = (List<LightSchedule>) session.getAttribute("lightSchedules");
        if (lightScheduleId == 0) {
            // Tạo mới lịch chiếu sáng
            lightSchedules.add(lightSchedule);
        } else {
            // Cập nhật lịch chiếu sáng cũ
            for (LightSchedule ls : lightSchedules) {
                if (ls.getId() == lightScheduleId) {
                    ls.setStartTime(lightSchedule.getStartTime());
                    ls.setEndTime(lightSchedule.getEndTime());
                    ls.setLightIntensity(lightSchedule.getLightIntensity());
                    break;
                }
            }
        }
        return "redirect:/schedules/" + scheduleId;
    }

    @GetMapping("/schedules/recommended/")
    public String showRecommendedScheduleList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("plant", plantService.getAllPlants());
        return "recommendedSchedule";
    }

    @GetMapping("/schedules/recommended/{plantId}")
    public String showRecommendedSchedule(@ModelAttribute Plant plant, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "recommendedSchedule";
    }
}
