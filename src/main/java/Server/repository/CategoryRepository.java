package Server.repository;

import Server.entityFilm.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT c FROM Category c JOIN FETCH c.films WHERE c.name = :name")
    // == fetch eager
    Category findByName(String name);
}
