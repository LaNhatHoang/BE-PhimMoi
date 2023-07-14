package Server.repository;

import Server.data.ServerPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerPartRepository extends JpaRepository<ServerPart, Integer> {
}
