package dungnm243.webserver.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PotStatistic {
    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime time;
    private double currentWaterLevel;
    private double currentMoisture;
    private double currentLightIntensity;
    private boolean waterLevelLow;
    private boolean lightOn;
    private boolean pumpOn;
}
