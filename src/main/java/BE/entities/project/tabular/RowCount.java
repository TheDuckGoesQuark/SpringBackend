package BE.entities.project.tabular;

import BE.entities.project.MetaFile;

import javax.persistence.*;

@Table(name = "row_count")
public class RowCount {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    @Id
    private MetaFile file;

    private int rows;

    protected RowCount() {
    }

    public RowCount(MetaFile file, int rows) {
        this.file = file;
        this.rows = rows;
    }

    public MetaFile getFile() {
        return file;
    }

    public void setFile(MetaFile file) {
        this.file = file;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
