package erykmarnik.assignments.subject.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

interface SubjectRepository extends JpaRepository<Subject, Long> {

  @Query("SELECT s FROM Subject s WHERE s.subjectName =: subjectName")
  Optional<Subject> findBySubjectName(@Param("subjectName") String subjectName);

  List<Subject> findAllBySubjectIdIn(Collection<Long> subjectsIds);
}
