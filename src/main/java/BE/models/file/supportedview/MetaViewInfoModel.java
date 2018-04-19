package BE.models.file.supportedview;

import BE.entities.project.SupportedView;

public class MetaViewInfoModel extends SupportedViewMeta {
    @Override
    public String getName() {
        return SupportedView.META_VIEW;
    }
}
