package BE.entities.project;


import javax.persistence.*;

@Entity
@Table(name = "supported_view")
public class SupportedView {

    public static final String META_VIEW = "meta";
    public static final String RAW_VIEW = "raw";

    @Id
    private String view;

    protected SupportedView() {
    }

    public SupportedView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
