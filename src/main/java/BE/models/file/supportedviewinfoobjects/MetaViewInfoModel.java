package BE.models.file.supportedviewinfoobjects;

import BE.entities.project.SupportedView;

public class MetaViewInfoModel extends BaseSupportedViewInfo {
    @Override
    public String getName() {
        return SupportedView.META_VIEW;
    }
}
