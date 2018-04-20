package BE.entities.project;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

       @Id
        private String role;
        private String description;

    @Column(columnDefinition = "TINYINT(1)")
        private boolean internal;

        protected Role() {}

        public Role(String role, String description, boolean internal) {
            this.role = role;
            this.description = description;
            this.internal = internal;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isInternal() {
            return internal;
        }

}
