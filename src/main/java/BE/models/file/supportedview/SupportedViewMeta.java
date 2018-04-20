package BE.models.file.supportedview;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class SupportedViewMeta {

    @JsonIgnore
    public abstract String getName();

}
