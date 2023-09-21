package Server.entityFilm;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String nameEng;
    private String url;
    private int year;
    private String sub;
    private String type;
    private int view;
    private int star;
    private int share;
    private int review;
    private String urlImage;
    private String urlImage500;
    private String urlImagePhimmoi;
    private String urlImageTmdb;
    private String urlImageTmdb500;
    private int time;
    private String country;
    private String legion;
    private String rated;
    private String urlTrailer;
    private Date createAt;
    private Date updateAt;
    @Column(columnDefinition = "text")
    private String descriptions;

    @ManyToMany
    private Set<Category> categories;

    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    private List<Server> servers;

    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    private List<Part> parts;

    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    private List<Director> directors;

    @OneToMany(mappedBy = "film", cascade = CascadeType.REMOVE)
    private List<Actor> actors;

}
