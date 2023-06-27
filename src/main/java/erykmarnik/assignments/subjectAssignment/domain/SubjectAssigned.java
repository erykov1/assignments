package erykmarnik.assignments.subjectAssignment.domain;

import erykmarnik.assignments.subjectAssignment.dto.SubjectAssignedDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "subject_assigned")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class SubjectAssigned {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  Long subjectId;

  String subjectName;

  @OneToMany(mappedBy = "userIds")
  private Set<SubjectAssignment> subjectAssignments;

  SubjectAssignedDto dto() {
    return new SubjectAssignedDto(subjectId, subjectName);
  }

}
