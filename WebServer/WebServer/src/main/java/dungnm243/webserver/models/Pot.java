package dungnm243.webserver.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pot {
    @Id
    @GeneratedValue
    private long id;
    private String potName;
    @ManyToOne
    private Plant plant;
    @ManyToOne
    private Schedule schedule;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    @OneToMany
    private List<PotStatistic> potStatistic;
}
