package Entity;


// Change to your actual package

import jakarta.persistence.*; // Required for @Entity, @Table, @Id in Spring Boot 3
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // Ensure you have a Role enum created!

    // --- UserDetails Interface Methods ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // This converts your "ROLE_USER" enum into a permission Spring understands
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email; // We use email to login, not a separate username
    }

    @Override
    public String getPassword() {
        return this.password; // <--- FIXED THIS LINE
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}