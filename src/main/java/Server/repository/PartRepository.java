package Server.repository;

import Server.entityFilm.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Integer> {
}
