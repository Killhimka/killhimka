package org.example.killhimka.repository.user;
;
import org.example.killhimka.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    User getUsersByLoginAndPassword(String login, String password);

    Optional<User> findByLogin(String login);
}
