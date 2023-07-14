package Server.repository;

import Server.data.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    Film findById(int id);

    @Query(value = "SELECT f.name, f.star, f.year, f.urlImage, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY f.id DESC LIMIT ?2")
    List<Object[]> phimTypeLimit(String type, int limit);

    @Query(value = "SELECT f.name, f.nameEng, f.sub, f.urlImage, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY f.updateAt DESC LIMIT ?2")
    List<Object[]> phimTypeUpdateLimit(String type, int limit);

    @Query(value = "SELECT f.name, f.sub, f.year, f.urlImage500, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY f.updateAt DESC LIMIT ?2")
    List<Object[]> phimTypeSlideShowUpdateLimit(String type, int limit);

    @Query(value = "SELECT f.name, f.nameEng, f.sub, f.urlImage, f.url, f.type FROM Film f JOIN f.categorys c WHERE c.type = ?1 ORDER BY f.id DESC LIMIT ?2")
    List<Object[]> phimCategoryLimit(String categoryType, int limit);

    @Query(value = "SELECT f.name, f.year, f.sub, f.urlImage, f.url, f.type FROM Film f ORDER BY f.view DESC LIMIT ?1")
    List<Object[]> phimTrendLimit(int limit);

    @Query(value = "SELECT f.name, f.year, f.sub, f.urlImage, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY f.view DESC LIMIT ?2")
    List<Object[]> phimTypeTrendLimit(String type, int limit);

    @Query(value = "SELECT f FROM Film f WHERE f.type = ?1 AND f.url = ?2")
    Film phimTypeUrl(String type, String url);

    @Query(value = "SELECT f.name, f.nameEng, f.urlImage, f.sub, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY f.id DESC")
    Page<Object[]> phimTypePage(String type, Pageable pageable);
}