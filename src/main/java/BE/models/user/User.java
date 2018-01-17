package BE.models.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="user")
public class User implements Serializable{
    @Id
    @Column(name="username")
    private String username;

    protected User() {
    }

    public User(String username) {
        this.username = username;
    }
}
