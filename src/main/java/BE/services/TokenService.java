package BE.services;

import BE.responsemodels.security.TokenModel;
import BE.responsemodels.user.UserModel;

import java.util.List;

public interface TokenService {

    public void deleteToken(String tokenId);

    public TokenModel getTokenById(String tokenId);

    public TokenModel allocateToken(String username);

    public TokenModel refreshToken(String tokenId);

    public UserModel getUserFromTokenId(String tokenId);
}
