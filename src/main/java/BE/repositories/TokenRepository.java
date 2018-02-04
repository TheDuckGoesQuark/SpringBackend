package BE.repositories;

import BE.entities.security.Token;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends CrudRepository<Token, String> {

    @Query(
            value = "DELETE FROM oauth_access_token WHERE TIMESTAMPDIFF(SECOND, created, NOW()) > :duration",
            nativeQuery = true
    )
    void removeExpiredTokens(@Param("duration") int duration);

    @Modifying
    @Query(
            value = "SELECT * FROM oauth_access_token t WHERE t.refresh_token = :refresh_token",
            nativeQuery = true
    )
    Token findByRefresh_token(@Param("refresh_token") String refresh_token);
}
