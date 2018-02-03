package BE.repositories;

import BE.entities.security.Token;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends CrudRepository<Token, String> {

    public Token findByToken_id(String token_id);

    @Query(
            value = "DELETE FROM oauth_access_token t WHERE TIMESTAMPDIFF(SECOND, t.created, NOW()) > :duration",
            nativeQuery = true
    )
    void removeExpiredTokens(@Param("duration") int duration);

    public Token findByRefresh_token(String refresh_token_id);
}
