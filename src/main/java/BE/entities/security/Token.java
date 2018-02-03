package BE.entities.security;

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

    @OneToOne
    @JoinColumn(name = "username")
    private String username;

    @CreationTimestamp
    private Timestamp created;

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String refresh_token;

    protected Token() {
    }

    public Token(String username, Timestamp created) {
        this.username = username;
        this.created = created;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }


}
