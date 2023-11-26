package dungnm243.webserver.restControllers;

import dungnm243.webserver.models.Pot;
import dungnm243.webserver.services.PotService;
import org.springframework.web.bind.annotation.*;

@RestController
public class IoTRestController {


    private final PotService potService;

    public IoTRestController(PotService potService) {
        this.potService = potService;
    }

    @PostMapping("/update/{potId}")
    @ResponseBody
    public String processUpdate(@PathVariable long potId, @RequestBody String requestData) {
        Pot pot = potService.getPotById(potId);

        // Create a response
        String responseData = pot.getSchedule().toString();
        return responseData;
    }

}
