//package BE.security;
//
//import BE.exceptions.AuthorisationFailureTypes;
//import BE.exceptions.NotAuthorisedException;
//import BE.responsemodels.security.TokenModel;
//import BE.responsemodels.security.TokenRequestModel;
//import BE.services.TokenService;
//import BE.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    final
//    UserService userService;
//
//    final
//    TokenService tokenService;
//
//    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
//    @Autowired
//    public CustomAuthenticationProvider(TokenService tokenService, UserService userService) {
//        this.tokenService = tokenService;
//        this.userService = userService;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        Object credentials = authentication.getCredentials();
//        if (!(credentials instanceof String)) return null;
//        String password = credentials.toString();
//        // TODO implement hashing and salting of passwords
//        UserDetails user = userService.loadUserByUsername(username);
//        if (!user.getPassword().equals(password)) throw new NotAuthorisedException(AuthorisationFailureTypes.INVALID_REQUEST);
//
//        TokenModel tokenModel = tokenService.allocateToken(user.getUsername());
//        authentication.setAuthenticated(true);
//        return authentication;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return TokenRequestModel.class.isAssignableFrom(authentication);
//    }
//}
