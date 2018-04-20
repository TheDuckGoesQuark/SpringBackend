package BE.models.file.supportedview;

import BE.entities.project.SupportedView;

public class RawViewInfoModel extends SupportedViewMeta {

    private long size;

    public RawViewInfoModel(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String getName() {
        return SupportedView.RAW_VIEW;
    }
}
