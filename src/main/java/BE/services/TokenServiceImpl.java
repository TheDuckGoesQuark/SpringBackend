package BE.services;

import BE.entities.security.Token;
import BE.entities.user.User;
import BE.exceptions.TokenExpiredException;
import BE.exceptions.TokenNotFoundException;
import BE.exceptions.UserNotFoundException;
import BE.repositories.TokenRepository;
import BE.repositories.UserRepository;
import BE.responsemodels.security.TokenModel;
import BE.responsemodels.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class TokenServiceImpl implements TokenService {

    private final
    TokenRepository tokenRepository;

    private final
    UserRepository userRepository;

    private final
    UserService userService;

    private final int REMOVE_EXPIRED_TOKENS_DELAY_MILLISECONDS = 60000; // 1 minute
    private final int TOKEN_LIFETIME_MILLISECONDS = 21600000; // 6 hours

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository, UserRepository userRepository, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    @Scheduled(fixedDelay = REMOVE_EXPIRED_TOKENS_DELAY_MILLISECONDS, initialDelay = REMOVE_EXPIRED_TOKENS_DELAY_MILLISECONDS)
    public void removeExpiredTokens() {
        tokenRepository.removeExpiredTokens(TOKEN_LIFETIME_MILLISECONDS);
    }

    private static int calcTimeSinceCreation(java.sql.Timestamp time_of_creation) {
        long milliseconds1 = time_of_creation.getTime();
        long milliseconds2 = System.currentTimeMillis();
        Long diff = (milliseconds2 - milliseconds1) / 1000;
        return diff.intValue();
    }

    private Token generateToken(User user) {
        Token token = new Token(user, new Timestamp(System.currentTimeMillis()));
        return tokenRepository.save(token);
    }

    private TokenModel tokenToTokenModel(Token token) {
        return new TokenModel(
                null,
                token.getToken_id(),
                token.getRefresh_token(),
                calcTimeSinceCreation(token.getCreated())
        );
    }

    @Override
    @Transactional
    public void deleteToken(String tokenId) {
        tokenRepository.delete(tokenId);
    }

    @Override
    public TokenModel getTokenById(String tokenId) {
        Token token = tokenRepository.findOne(tokenId);
        if (token == null) throw new TokenNotFoundException();
        return tokenToTokenModel(token);
    }

    @Override
    @Transactional
    public TokenModel allocateToken(String username) {
        Token token = generateToken(userRepository.findByUsername(username));
        return tokenToTokenModel(token);
    }

    @Transactional
    public TokenModel refreshToken(String tokenId) {
        Token token = tokenRepository.findByRefresh_token(tokenId);
        if (token == null) throw new TokenNotFoundException();
        if (calcTimeSinceCreation(token.getCreated()) > TOKEN_LIFETIME_MILLISECONDS) throw new TokenExpiredException();
        token = generateToken(token.getUser());
        return tokenToTokenModel(token);
    }

    @Override
    public UserModel getUserFromTokenId(String tokenId) {
        Token token = tokenRepository.findOne(tokenId);
        if (token == null) throw new TokenNotFoundException();
        UserModel user = userService.getUserByUserName(token.getUser().getUsername());
        if (user == null) throw new UserNotFoundException();
        return user;
    }
}
