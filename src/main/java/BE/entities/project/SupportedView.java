package BE.entities.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "supported_view")
public class SupportedView {

    public static final String META_VIEW = "meta";
    public static final String RAW_VIEW = "raw";
    public static final List<SupportedView> DIRECTORY_SUPPORTED_VIEWS = ImmutableList.of(new SupportedView(META_VIEW));
    public static final List<SupportedView> FILE_SUPPORTED_VIEWS = ImmutableList.of(new SupportedView(META_VIEW), new SupportedView(RAW_VIEW));
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
