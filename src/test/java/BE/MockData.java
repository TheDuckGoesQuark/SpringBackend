package BE;

import BE.entities.UserProject;
import BE.entities.user.Privilege;
import BE.entities.user.User;

import java.util.Collections;

public final class MockData {

    public static final Privilege[] PRIVILEGES = new Privilege[]{
            new Privilege("user", "can do some things", false),
            new Privilege("admin", "can do all the things", true)
    };

    public static final User[] USERS = new User[]{
            new User("peter", "vardy", "car@store", Collections.emptyList(), Collections.emptyList()),
            new User("digby", "brown", "injury@lawyers", Collections.emptyList(), Collections.emptyList())
    };
}
