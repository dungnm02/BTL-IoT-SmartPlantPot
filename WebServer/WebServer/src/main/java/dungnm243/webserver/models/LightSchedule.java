package dungnm243.webserver.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LightSchedule {
    @Id
    @GeneratedValue
    private long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private double lightIntensity;
}
