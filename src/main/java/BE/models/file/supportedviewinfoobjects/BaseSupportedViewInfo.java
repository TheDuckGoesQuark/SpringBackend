package BE.models.file.supportedviewinfoobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class BaseSupportedViewInfo {

    @JsonIgnore
    public abstract String getName();

}
