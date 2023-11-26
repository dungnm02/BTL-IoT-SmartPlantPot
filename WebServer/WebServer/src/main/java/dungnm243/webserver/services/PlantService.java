package dungnm243.webserver.services;

import dungnm243.webserver.models.Plant;
import dungnm243.webserver.models.Pot;
import dungnm243.webserver.models.Schedule;
import dungnm243.webserver.repos.PlantRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class PlantService {
    private final PlantRepo plantRepo;

    public PlantService(PlantRepo plantRepo) {
        this.plantRepo = plantRepo;
    }

    public Plant getPlantById(long id) {
        return plantRepo.findById(id).orElse(null);
    }

    public List<Plant> getAllPlants() {
        List<Plant> plants = plantRepo.findAll();
        for (Plant plant : plants) {
            plant.setPreferedSchedule(new Schedule());
        }
        return plants;
    }

    public List<Plant> getPlantsInPotList(List<Pot> pots) {
        HashSet<Plant> plants = new HashSet<>();
        for (Pot pot : pots) {
            plants.add(pot.getPlant());
        }
        return new ArrayList<>(plants);
    }

}

