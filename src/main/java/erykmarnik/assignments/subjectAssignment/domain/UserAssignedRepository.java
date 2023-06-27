package erykmarnik.assignments.subjectAssignment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

interface UserAssignedRepository extends JpaRepository<UserAssigned, Long> {

  @Query("SELECT ua FROM UserAssigned ua WHERE ua.userId IN :userIds AND ua.userRole = 2")
  List<UserAssigned> findAllStudentsByIdIn(@Param("userIds") Collection<Long> userIds);

}
