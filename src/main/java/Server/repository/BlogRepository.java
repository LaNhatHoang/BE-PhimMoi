package Server.repository;

import Server.entityFilm.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Blog findByUrl(String url);
    @Query(value = "SELECT b.name, b.description, b.url FROM Blog b")
    List<Object[]> blogAll();
    @Query(value = "SELECT b.name, b.description, b.url FROM Blog b ORDER BY b.id LIMIT ?1")
    List<Object[]> blogLimit(int limit);
}
