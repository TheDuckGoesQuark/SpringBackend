package BE.services;

import BE.entities.security.Token;
import BE.exceptions.TokenNotFoundException;
import BE.repositories.TokenRepository;
import BE.responsemodels.security.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService {

    private final
    TokenRepository tokenRepository;

    private final
    UserService userService;

    public static int subtractTimeStamps(java.sql.Timestamp newTime, java.sql.Timestamp oldTime) {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = newTime.getTime();
        Long diff = (milliseconds2 - milliseconds1) / 1000;
        return diff.intValue();
    }

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository, UserService userService) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }

    @Override
    public void deleteToken(String tokenId) {
        tokenRepository.delete(tokenId);
    }

    @Override
    public TokenModel getTokenById(String tokenId) {
        Token token = tokenRepository.findByToken_id(tokenId);
        if (token == null) throw new TokenNotFoundException();
        else return new TokenModel(null,
                tokenId, token.getRefresh_token(),
                subtractTimeStamps(new Timestamp(System.currentTimeMillis()), token.getCreated()));
    }

    @Override
    public TokenModel allocateToken(String username) {
        return null;
    }
}
