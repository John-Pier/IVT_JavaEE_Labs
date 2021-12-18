package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Entity(name = "compositions")
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compositions_id_seq")
    @SequenceGenerator(name="compositions_id_seq", sequenceName = "compositions_id_seq", allocationSize = 5)
    private int id;

    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Time duration;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Album album;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }
}
