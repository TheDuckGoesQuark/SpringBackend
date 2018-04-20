package BE.models.file;

public class SupportsViewModel {

    private boolean supports_view;

    public SupportsViewModel(boolean supports_view) {
        this.supports_view = supports_view;
    }

    public boolean isSupportedView() {
        return supports_view;
    }

    public void setSupports_view(boolean supports_view) {
        this.supports_view = supports_view;
    }
}
