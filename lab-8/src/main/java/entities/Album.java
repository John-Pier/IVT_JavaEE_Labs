package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "albums_id_seq")
    @SequenceGenerator(name="albums_id_seq", sequenceName = "albums_id_seq", allocationSize = 5)
    private int id;

    @Column(nullable = false)
    @NotNull
    private String name;

    private String genre;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "album")
    private List<Composition> compositionList = new java.util.ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Artist artist;

    public Artist getArtist() {
        return artist;
    }

    public List<Composition> getCompositionList() {
        return compositionList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
