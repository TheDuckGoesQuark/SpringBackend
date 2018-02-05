//package BE.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//
//import java.util.stream.Collectors;
//
//public class SpringSecurityCustomUser extends User{
//    public SpringSecurityCustomUser(BE.entities.user.User user) {
//        super(user.getUsername(),
//                user.getPassword(),
//                user.getPrivileges().stream().map(privilege -> (GrantedAuthority) privilege::getName).collect(Collectors.toList()));
//    }
//
//    public SpringSecurityCustomUser(BE.entities.user.User user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {
//        super(user.getUsername(),
//                user.getPassword(),
//                enabled,
//                accountNonExpired,
//                credentialsNonExpired,
//                accountNonLocked,
//                user.getPrivileges().stream().map(privilege -> (GrantedAuthority) privilege::getName).collect(Collectors.toList()));
//    }
//}
