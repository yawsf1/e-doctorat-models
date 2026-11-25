package ma.edoct.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity @Table(name = "directeur_pole_calendrier")
public class Calendrier {
    @Id public Integer id;
    @Column(nullable = false, length = 500) public String action;
    @Column(name = "dateDebut", nullable = false) public LocalDate dateDebut;
    @Column(name = "dateFin", nullable = false) public LocalDate dateFin;
    @Column(nullable = false, length = 50) public String pour;
    protected Calendrier() {}

    public Integer getId() { return id; }
    public String getAction() { return action; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public String getPour() { return pour; }
}

