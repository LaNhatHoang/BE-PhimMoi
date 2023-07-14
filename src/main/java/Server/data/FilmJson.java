package Server.data;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmJson {
    private String name;
    private String nameEng;
    private String url;
    private int year;
    private String sub;
    private String type;
    private String urlImage;
    private int time;
    private String country;
    private String rated;
    private String legion;
    private String urlTrailer;
    private List<String> descriptions;
    private List<Category> categorys;

    private List<Server> servers;

    private List<Part> parts;

    private List<Director> directors;

    private List<Actor> actors;
}
