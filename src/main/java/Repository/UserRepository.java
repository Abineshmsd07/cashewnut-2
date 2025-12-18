package Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import Entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Finds a user by email (select * from users where email = ?)
    Optional<User> findByEmail(String email);
    
    // Checks if an email exists (useful during registration)
    boolean existsByEmail(String email);
}
