package BE.entities.project.tabular;

import BE.entities.project.MetaFile;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "row_count")
public class RowCount implements Serializable {

    @OneToOne
    @JoinColumn(name="file_id")
    private MetaFile file;

    @Id
    @Column(name="file_id")
    private int file_id; // duplicate field needed so that crudrepo would recognise the id

    private int rows;

    protected RowCount() {
    }

    public RowCount(MetaFile file, int rows) {
        this.file = file;
        this.rows = rows;
    }


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setFile(MetaFile file) {
        this.file = file;
    }

    public MetaFile getFile() {
        return file;
    }
}
