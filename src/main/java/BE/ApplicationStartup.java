package BE;

import BE.responsemodels.user.UserModel;
import BE.security.enums.Privileges;
import BE.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

    final
    UserService userService;

    @Autowired
    public ApplicationStartup(UserService userService) {
        this.userService = userService;
    }

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        String[] userPrivileges = {Privileges.USER};
        String[] adminPrivileges = {Privileges.USER, Privileges.ADMIN};
        UserModel userModel = new UserModel("username1", "password1", "email", null, Arrays.asList(userPrivileges));
        UserModel adminModel = new UserModel("admin1", "password1", "email", null, Arrays.asList(adminPrivileges));
        userService.createUser(userModel);
        userService.createUser(adminModel);
    }
}
