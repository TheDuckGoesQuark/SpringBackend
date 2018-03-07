package BE.models.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class TokenHeaderModel extends AbstractAuthenticationToken {

    private String access_token;


    public TokenHeaderModel(Collection<? extends GrantedAuthority> authorities, String access_token) {
        super(authorities);
        this.access_token = access_token;
    }

    @Override
    public Object getCredentials() {
        return access_token;
    }

    @Override
    public Object getPrincipal() {
        return access_token;
    }
}
