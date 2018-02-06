package BE.entities.security;

import BE.entities.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "oauth_access_token")
public class Token {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String token_id;

    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "username")
    private User user;

    @CreationTimestamp
    private Timestamp created;

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String refresh_token;

    protected Token() {
    }

    public Token(User user, Timestamp created) {
        this.user = user;
        this.created = created;
    }

    public Token(String token_id, User user, Timestamp created, String refresh_token) {
        this.token_id = token_id;
        this.user = user;
        this.created = created;
        this.refresh_token = refresh_token;
    }

    public String getToken_id() {
        return token_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreated() {
        return created;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

}
