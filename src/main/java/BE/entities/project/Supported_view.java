package BE.entities.project;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(Supported_view.class)
@Table(name = "Supported_view")
public class Supported_view implements Serializable{
    @Id
    @JoinColumn(name = "file_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private File file; // this may have to be FILE

    @Id
    private String view;

    public Supported_view(File file, String view) {
        this.file = file;
        this.view = view;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
