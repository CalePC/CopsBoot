package cpc.uv.copsboot.user;

import cpc.uv.orm.jpa.AbstractEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table(name = "copsboot_user")
public class User extends AbstractEntity<UserId> {

    private String name;
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<UserRole> roles;

    protected User() {}

    public User(UserId id, String name, String email, String password, Set<UserRole> roles) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Set<UserRole> getRoles() {
        return roles;
    }

}
