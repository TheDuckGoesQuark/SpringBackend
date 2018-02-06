package BE.security;

import BE.exceptions.NotAuthorisedException;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {

    private NotAuthorisedException notAuthorisedException;

    public CustomAuthenticationException(NotAuthorisedException e) {
        super(e.getError_description());
        this.notAuthorisedException = e;
    }

    public NotAuthorisedException getNotAuthorisedException() {
        return notAuthorisedException;
    }
}
