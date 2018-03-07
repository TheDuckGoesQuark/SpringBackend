package BE.services;

import BE.entities.security.Token;
import BE.entities.user.User;
import BE.exceptions.TokenExpiredException;
import BE.exceptions.TokenNotFoundException;
import BE.exceptions.UserNotFoundException;
import BE.repositories.TokenRepository;
import BE.repositories.UserRepository;
import BE.models.security.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    private final
    TokenRepository tokenRepository;

    private final
    UserRepository userRepository;

    private final int REMOVE_EXPIRED_TOKENS_DELAY_MILLISECONDS = 60000; // 1 minute
    private final int TOKEN_LIFETIME_MILLISECONDS = 21600000; // 6 hours

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    /**
     * Removes tokens that have expired
     */
    @Transactional
    @Scheduled(fixedDelay = REMOVE_EXPIRED_TOKENS_DELAY_MILLISECONDS, initialDelay = REMOVE_EXPIRED_TOKENS_DELAY_MILLISECONDS)
    public void removeExpiredTokens() {
        tokenRepository.removeExpiredTokens(TOKEN_LIFETIME_MILLISECONDS);
    }

    /**
     * Calculates the time since a token was created
     * @param time_of_creation time of token creation
     * @return time in seconds since creation
     */
    private static int calcTimeSinceCreation(java.sql.Timestamp time_of_creation) {
        long milliseconds1 = time_of_creation.getTime();
        long milliseconds2 = System.currentTimeMillis();
        Long diff = (milliseconds2 - milliseconds1) / 1000;
        return diff.intValue();
    }

    /**
     * Generates a token for a specific user
     * @param user the user to generate token for
     * @return token
     */
    private Token generateToken(User user) {
        Token token = new Token(user, UUID.randomUUID().toString());
        return tokenRepository.save(token);
    }

    /**
     * Converts a specific token to a token model
     * @param token the token to be converted
     * @return token model
     */
    private TokenModel tokenToTokenModel(Token token) {
        return new TokenModel(
                token.getToken_id(),
                token.getRefresh_token(),
                TOKEN_LIFETIME_MILLISECONDS - calcTimeSinceCreation(token.getCreated())
        );
    }

    /**
     * Deletes a specific token
     * @param tokenId the id of the token to be deleted
     */
    @Override
    @Transactional
    public void deleteToken(String tokenId) {
        tokenRepository.delete(tokenId);
    }

    /**
     * Gets a specific token by token id
     * @param tokenId the id of the token to get
     * @return token
     */
    @Override
    public TokenModel getTokenById(String tokenId) {
        Token token = tokenRepository.findOne(tokenId);
        if (token == null) throw new TokenNotFoundException();
        return tokenToTokenModel(token);
    }

    /**
     * Allocates a token to a user
     * @param username username of user to have token allocated to
     * @return token
     */
    @Override
    @Transactional
    public TokenModel allocateToken(String username) {
        Token token = generateToken(userRepository.findByUsername(username));
        return tokenToTokenModel(token);
    }

    /**
     * Refreshes a specific token
     * @param tokenId id of the token to be refreshed
     * @return token
     */
    @Transactional
    public TokenModel refreshToken(String tokenId) {
        Token token = tokenRepository.findByRefresh_token(tokenId);
        if (token == null) throw new TokenNotFoundException();
        if (calcTimeSinceCreation(token.getCreated()) > TOKEN_LIFETIME_MILLISECONDS) throw new TokenExpiredException();
        token = generateToken(token.getUser());
        return tokenToTokenModel(token);
    }

    /**
     * Gets the username of a user from the token id of a specific token
     * @param tokenId id of the token to get user's username from
     * @return username
     */
    @Override
    public String getUsernameFromTokenId(String tokenId) {
        Token token = tokenRepository.findOne(tokenId);
        if (token == null) throw new TokenNotFoundException();
        String user = token.getUser().getUsername();
        if (user == null) throw new UserNotFoundException();
        return user;
    }
}
