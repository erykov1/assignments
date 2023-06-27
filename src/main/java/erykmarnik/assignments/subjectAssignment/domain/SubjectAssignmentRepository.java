package erykmarnik.assignments.subjectAssignment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


interface SubjectAssignmentRepository extends JpaRepository<SubjectAssignment, Long> {

  Optional<SubjectAssignment> findBySubjectId(Long subjectId);

  @Query("SELECT s.userIds FROM SubjectAssignment s WHERE s.userIds IN :userIds")
  List<Long> findAllAssignedStudents(@Param("userIds") Collection<Long> userIds);

  @Query("SELECT unnest(s.userIds) FROM SubjectAssignment s WHERE s.subjectId = :subjectId")
  List<Long> findAllAssignedStudentsInGivenSubject(@Param("subjectId") Long subjectId);

}
