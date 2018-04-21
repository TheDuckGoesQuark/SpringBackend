package BE.config;

import BE.entities.project.SupportedView;
import BE.models.MetaDataModel;
import BE.models.user.UserModel;
import BE.repositories.SupportedViewRepository;
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
        // TODO GET RID OF THESE MAJOR SECURITY HOLES
        if (!userService.usernameExists("username1")) {
            UserModel userModel = new UserModel("username1", "password1", "email", null, Arrays.asList(userPrivileges));
            userService.createUser(userModel);
        }
        if (!userService.usernameExists("admin1")) {
            UserModel adminModel = new UserModel("admin1", "password1", "email", null, Arrays.asList(adminPrivileges));
            userService.createUser(adminModel);
        }
    }
}
