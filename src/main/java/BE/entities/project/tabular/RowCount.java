package BE.entities.project.tabular;

import BE.entities.project.MetaFile;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "row_count")
public class RowCount implements Serializable {

    @MapsId
    @OneToOne
    @JoinColumn(name = "file_id", nullable = false)
    private MetaFile file;

    @Id
    @Column(name = "file_id", nullable = false)
    private int file_id; // duplicate field needed so that crudrepo would recognise the id

    @Column(name="\"rows\"")
    private int rows;

    protected RowCount() {
    }

    public RowCount(MetaFile file, int rows) {
        this.file = file;
        this.rows = rows;
        this.file_id = file.getFileId();
    }

    public RowCount(int rows) {
        this.rows = rows;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setFile(MetaFile file) {
        this.file = file;
        this.file_id = file.getFileId();
    }

    public MetaFile getFile() {
        return file;
    }
}
