/*
package BE.services;

import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

*/
/**
 * Created by lavi on 29.03.2015.
 *//*

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    @Transactional
    public Token getTokenByKey(String tokenKey) {
        return tokenRepository.findByKey(tokenKey);
    }

    @Transactional
    public List<Token> getTokens() {
        return tokenRepository.findAll();
    }

    @Override
    @Transactional
    public void addToken(Token token) {
        tokenRepository.saveAndFlush(token);
    }

    @Override
    public Token generateToken(Date expiredDate) {
        Token token = new Token();
        token.setExpired(expiredDate);
        token.setCreated(new Date());
        token.setKey(UUID.randomUUID().toString().toUpperCase());
        token.setToken(PasswordEncryption.encrypt(String.valueOf(System.nanoTime())));
        return token;
    }

    @Override
    @Transactional
    public void updateLastLoginByCurrentDate(Token token) {
        token.setLastUsed(new Date());
        tokenRepository.saveAndFlush(token);
    }

    @Override
    @Transactional
    public void updateToken(Token token) {
        tokenRepository.saveAndFlush(token);
    }

    @Transactional
    public void deleteToken(Integer tokenId) {
        tokenRepository.delete(tokenId);
    }

    @Transactional
    public Page<Token> getTokens(Pageable pageable) {
        return tokenRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Token getTokenById(Integer tokenId) {
        return tokenRepository.findOne(tokenId);
    }

    @Override
    public Token allocateToken(String s) {
        return null;
    }

    @Override
    public Token verifyToken(String s) {
        return null;
    }
}*/
