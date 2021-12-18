package entities;

import javax.persistence.*;

@Entity(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artists_id_seq")
    @SequenceGenerator(name="artists_id_seq", sequenceName = "artists_id_seq", allocationSize = 5)
    private int id;

    @Column()
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
