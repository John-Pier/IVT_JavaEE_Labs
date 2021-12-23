package entities;

import javax.persistence.*;
import java.util.List;

@Table()
@Entity(name = "artists")
public class Artist implements EntityMarker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artists_id_seq")
    @SequenceGenerator(name="artists_id_seq", sequenceName = "artists_id_seq", allocationSize = 5)
    private int id;

    @Column()
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "artist")
    private List<Album> albumList = new java.util.ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(List<Album> albumList) {
        this.albumList = albumList;
    }
}
