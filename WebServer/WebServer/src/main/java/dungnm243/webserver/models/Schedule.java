package dungnm243.webserver.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String scheduleName;
    @Column(nullable = false)
    private double minWaterLevel;
    @Column(nullable = false)
    private double moisture;
    @OneToMany(fetch = FetchType.EAGER)
    private List<LightSchedule> lightSchedules;
    @ManyToOne
    private User user;

    public Schedule() {
        id = -1;
        scheduleName = "";
        minWaterLevel = 50;
        moisture = 50;
        lightSchedules = new ArrayList<>();
        user = null;
    }

    @Override
    public String toString() {
        String res = minWaterLevel + "\n" +
                moisture + "\n" +
                lightSchedules.size() + "\n";
        for (LightSchedule lightSchedule : lightSchedules) {
            res += lightSchedule.getStartTime() + " " + lightSchedule.getEndTime() + " " + lightSchedule.getLightIntensity() + "\n";
        }
        return res;
    }
}
