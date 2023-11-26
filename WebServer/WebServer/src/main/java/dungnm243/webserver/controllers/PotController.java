package dungnm243.webserver.controllers;

import dungnm243.webserver.models.Pot;
import dungnm243.webserver.models.User;
import dungnm243.webserver.services.PlantService;
import dungnm243.webserver.services.PotService;
import dungnm243.webserver.services.ScheduleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PotController {
    private final PotService potService;
    private final PlantService plantService;
    private final ScheduleService scheduleService;

    public PotController(PotService potService, PlantService plantService, ScheduleService scheduleService) {
        this.potService = potService;
        this.plantService = plantService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/pots")
    public String showPotList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Pot> userPots = potService.getPotsByUser(user);
        session.setAttribute("userPots", userPots);
        model.addAttribute("user", user);
        model.addAttribute("pots", userPots);
        return "pots";
    }

    @GetMapping("/pots/{potId}")
    public String showPotDetail(@PathVariable Long potId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Pot> userPots = (List<Pot>) session.getAttribute("userPots");
        for (Pot pot : userPots) {
            if (pot.getId() == potId) {
                model.addAttribute("pot", pot);
                session.setAttribute("pot", pot);
                break;
            }
        }
        model.addAttribute("plantList", plantService.getAllPlants());
        model.addAttribute("scheduleList", scheduleService.getSchedulesByUser((User) session.getAttribute("user")));
        return "pot";
    }

    @PostMapping("/pots/{potId}")
    public String updatePot(@ModelAttribute("pot") Pot updatedPot,
                            @RequestParam("plant") Long plantId, @RequestParam("schedule") Long scheduleId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Pot pot = (Pot) session.getAttribute("pot");
        pot.setPotName(updatedPot.getPotName());
        pot.setPlant(plantService.getPlantById(plantId));
        pot.setSchedule(scheduleService.getScheduleById(scheduleId));
        session.removeAttribute("pot");
        potService.savePot(pot);
        for (Pot p : (List<Pot>) session.getAttribute("userPots")) {
            if (p.getId() == pot.getId()) {
                p.setPlant(pot.getPlant());
                p.setSchedule(pot.getSchedule());
                break;
            }
        }
        return "redirect:/pots";
    }

    @GetMapping("/pots/add")
    public String showAddPot(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("potId", 0);
        return "addPot";
    }

    @PostMapping("/pots/add")
    public String addPot(@ModelAttribute("potId") long potId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        boolean result = potService.addPotToUser(potId, user);
        if (result) {
            return "redirect:/pots";
        } else {
            model.addAttribute("message", "Không tìm thấy chậu cây này");
            return "redirect:/pots/add/" + potId;
        }
    }
}
