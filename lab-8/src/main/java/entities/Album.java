package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Table()
@Entity(name = "albums")
public class Album implements EntityMarker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "albums_id_seq")
    @SequenceGenerator(name="albums_id_seq", sequenceName = "albums_id_seq", allocationSize = 5)
    private int id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = true)
    private String genre;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "album")
    private List<Composition> compositionList = new java.util.ArrayList<>();

    @ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.EAGER ,optional = false)
    @JoinColumn(nullable = false)
    private Artist artist;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Composition> getCompositionList() {
        return compositionList;
    }

    public void setCompositionList(List<Composition> compositionList) {
        this.compositionList = compositionList;
    }

    public void addComposition(Composition composition) {
        this.compositionList.add(composition);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
