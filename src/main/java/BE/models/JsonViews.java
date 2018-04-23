package BE.models;

public class JsonViews {
    public interface UserView {}
    public interface CurrentUserView extends UserView {}
    public interface AdminView extends CurrentUserView {}
}
