package BE.entities.project;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(Dir_contains.class)
@Table(name = "Dir_contains")
public class Dir_contains implements Serializable {
    @Id
    @JoinColumn(name = "dir_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private MetaFile dir;

    @Id
    @JoinColumn(name = "file_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private MetaFile metaFile;

    private int length;

    public Dir_contains() {
    }

    public Dir_contains(MetaFile dir, MetaFile metaFile, int length) {
        this.dir = dir;
        this.metaFile = metaFile;
        this.length = length;
    }

    public MetaFile getDir() {
        return dir;
    }

    public void setDir(MetaFile dir) {
        this.dir = dir;
    }

    public MetaFile getMetaFile() {
        return metaFile;
    }

    public void setMetaFile(MetaFile metaFile) {
        this.metaFile = metaFile;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
