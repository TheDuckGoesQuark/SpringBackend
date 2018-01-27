package BE.entities;

import BE.entities.project.Project;
import BE.entities.user.User;

import javax.persistence.*;

/**
 *  Since involved_in involves attribute,
 *  A seperate entity class had to be created to handle
 *  this relationship.
 */

@Entity
@Table(name = "involved_in")
@IdClass(UserProjectPK.class)
public class UserProject {

    @Id
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_name", referencedColumnName = "name")
    private Project project;

    @Column(name = "role")
    private String role;



}
