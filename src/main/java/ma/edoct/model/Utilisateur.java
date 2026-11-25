package ma.edoct.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "auth_user")
public class Utilisateur implements Serializable, UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Integer id;
    @Column(nullable = false, length = 128) public String password;
    public LocalDateTime last_login;
    @Column(nullable = false) public boolean is_superuser;
    @Column(nullable = false, unique = true, length = 150) public String username;
    @Column(nullable = false, length = 150) public String first_name;
    @Column(nullable = false, length = 150) public String last_name;
    @Column(nullable = false, length = 254) public String email;
    @Column(nullable = false) public boolean is_staff;
    @Column(nullable = false) public boolean is_active;
    @Column(nullable = false) public LocalDateTime date_joined;

    // Les groupes Django (auth_group) servent de rôles Spring Security.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "auth_user_groups",
            joinColumns = @JoinColumn(name = "user_id", insertable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "group_id", insertable = false, updatable = false)
    )
    public Set<GroupeUtilisateur> groupes = new HashSet<>();

    protected Utilisateur() {}

    @Override public String getUsername() { return username; }
    @Override public String getPassword() { return password; }
    @Override public boolean isEnabled() { return is_active; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isAccountNonExpired() { return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (groupes != null) {
            for (GroupeUtilisateur groupe : groupes) {
                if (groupe != null && groupe.name != null) {
                    authorities.add(new SimpleGrantedAuthority(groupe.name));
                }
            }
        }
        return authorities;
    }

    public Integer getId() { return id; }
    public Set<GroupeUtilisateur> getGroupes() { return groupes; }
}
