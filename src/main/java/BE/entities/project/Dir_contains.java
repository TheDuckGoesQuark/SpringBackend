package BE.entities.project;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(Dir_contains.class)
@Table(name = "Dir_contains")
public class Dir_contains implements Serializable {
    @Id
    @JoinColumn(name = "dir_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private File dir;

    @Id
    @JoinColumn(name = "file_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private File file;

    public Dir_contains() {
    }

    public Dir_contains(File dir, File file) {
        this.dir = dir;
        this.file = file;
    }

    public File getDir() {
        return dir;
    }

    public void setDir(File dir) {
        this.dir = dir;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
