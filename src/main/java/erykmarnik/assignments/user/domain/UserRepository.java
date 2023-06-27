package erykmarnik.assignments.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

  List<User> findAllByUserIdIn(Collection<Long> userId);

  Optional<User> findByUsername(String username);

}
