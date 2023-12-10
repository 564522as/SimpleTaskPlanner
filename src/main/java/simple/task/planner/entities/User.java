package simple.task.planner.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "first_name")
    @NotBlank(message = "First name must be pointed")
    private String firstName;
    @Column(name = "last_name")
    @NotBlank(message = "Last name must be pointed")
    private String lastName;
    @Column(name = "email")
    @NotBlank(message = "Email name must be pointed")
    @Email
    private String email;
    @Column(name = "password")
    @NotBlank(message = "Password must be pointed")
    private String password;
    @OneToMany(mappedBy = "author")
    private List<Task> relatedTasks;
    @OneToMany(mappedBy = "executor")
    private List<Task> executableTasks;
    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Task> getRelatedTasks() {
        return relatedTasks;
    }

    public List<Task> getExecutableTasks() {
        return executableTasks;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
