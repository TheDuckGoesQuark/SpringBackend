package BE.responsemodels.system;

import java.util.List;

public class SupportedProtocolListModel {
    private List<String> supported;
    private List<String> required;

    protected SupportedProtocolListModel() {}

    public SupportedProtocolListModel(List<String> supported, List<String> required) {
        this.supported = supported;
        this.required = required;
    }

    public List<String> getSupported() {
        return supported;
    }

    public void setSupported(List<String> supported) {
        this.supported = supported;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }
}
