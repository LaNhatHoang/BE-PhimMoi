package Server.repository;

import Server.entityFilm.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    Film findById(int id);
    Film findByName(String name);
    Film findByUrl(String url);
    Film findTopByOrderByIdDesc();

    @Query(value = "SELECT f.name, f.star, f.year, f.urlImage, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY f.id DESC LIMIT ?2")
    List<Object[]> phimTypeLimit(String type, int limit);

    @Query(value = "SELECT f.name, f.nameEng, f.sub, f.urlImage, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY f.updateAt DESC LIMIT ?2")
    List<Object[]> phimTypeUpdateLimit(String type, int limit);

    @Query(value = "SELECT f.name, f.sub, f.year, f.urlImage500, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY f.updateAt DESC LIMIT ?2")
    List<Object[]> phimTypeSlideShowUpdateLimit(String type, int limit);

    @Query(value = "SELECT f.name, f.nameEng, f.sub, f.urlImage, f.url, f.type FROM Film f JOIN f.categories c WHERE c.type = ?1 ORDER BY f.id DESC LIMIT ?2")
    List<Object[]> phimCategoryLimit(String categoryType, int limit);

    @Query(value = "SELECT f.name, f.year, f.sub, f.urlImage, f.url, f.type FROM Film f ORDER BY f.view DESC LIMIT ?1")
    List<Object[]> phimTrendLimit(int limit);

    @Query(value = "SELECT f.name, f.year, f.sub, f.urlImage, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY f.view DESC LIMIT ?2")
    List<Object[]> phimTypeTrendLimit(String type, int limit);

    @Query(value = "SELECT f FROM Film f WHERE f.type = ?1 AND f.url = ?2")
    Film phimTypeUrl(String type, String url);

    @Query(value = "SELECT f.name, f.nameEng, f.urlImage, f.sub, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY f.id DESC")
    Page<Object[]> phimTypePage(String type, Pageable pageable);

    @Query(value = "SELECT f.name, f.nameEng, f.urlImage, f.sub, f.url, f.type FROM Film f JOIN f.categories c WHERE c.type = ?1 ORDER BY f.id DESC")
    Page<Object[]> phimCategoryPage(String category, Pageable pageable);

    @Query(value = "SELECT f.name, f.nameEng, f.urlImage, f.sub, f.url, f.type FROM Film f WHERE f.legion = ?1 ORDER BY f.id DESC")
    Page<Object[]> phimLegionPage(String legion, Pageable pageable);

    @Query(value = "SELECT f.name, f.nameEng, f.urlImage, f.sub, f.url, f.type FROM Film f WHERE f.year = ?1 ORDER BY f.id DESC")
    Page<Object[]> phimYearPage(int year, Pageable pageable);

    @Query(value = "SELECT f.name, f.year, f.descriptions, f.urlImage, f.url, f.type FROM Film f " +
            "WHERE LOWER(f.name) LIKE %?1% " +
            "ORDER BY f.id DESC")
    Page<Object[]> phimSearch(String searchValue, Pageable pageable);

    @Query(value = "SELECT f.name, f.urlImage, f.url, f.type FROM Film f WHERE f.type = ?1 ORDER BY RAND() LIMIT ?2")
    List<Object[]> phimTypeRandom(String type, int limit);
}
