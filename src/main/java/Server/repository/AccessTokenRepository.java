package Server.repository;

import Server.entity.AccessToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {
    AccessToken findByAccessToken(String accessToken);
    @Transactional
    void deleteByUserId(int id);
}
