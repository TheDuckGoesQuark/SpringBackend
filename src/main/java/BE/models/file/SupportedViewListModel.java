package BE.models.file;

import java.util.HashMap;
import java.util.Map;

public class SupportedViewListModel {

    private Map<String, TabularViewInfoModel> supported_views = new HashMap<>();

    public SupportedViewListModel() {
    }

    public SupportedViewListModel(Map<String, TabularViewInfoModel> supported_views) {
        this.supported_views = supported_views;
    }

    public Map<String, TabularViewInfoModel> getSupported_views() {
        return supported_views;
    }

    public void setSupported_views(Map<String, TabularViewInfoModel> supported_views) {
        this.supported_views = supported_views;
    }

    public void addSupportedView(String name, TabularViewInfoModel model) {
        this.supported_views.put(name, model);
    }
}
