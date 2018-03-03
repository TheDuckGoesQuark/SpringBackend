package BE.responsemodels.file;

import java.sql.Timestamp;

public class FileMetaDataModel {
    private Timestamp last_modified;
    private long length;

    public FileMetaDataModel(Timestamp last_modified, long length) {
        this.last_modified = last_modified;
        this.length = length;
    }

    public Timestamp getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(Timestamp last_modified) {
        this.last_modified = last_modified;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
