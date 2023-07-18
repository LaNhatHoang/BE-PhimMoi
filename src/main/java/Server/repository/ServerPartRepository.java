package Server.repository;

import Server.entityFilm.ServerPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerPartRepository extends JpaRepository<ServerPart, Integer> {
}
