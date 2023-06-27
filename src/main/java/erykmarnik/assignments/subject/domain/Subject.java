package erykmarnik.assignments.subject.domain;

import erykmarnik.assignments.subject.dto.SubjectCreatedEvent;
import erykmarnik.assignments.subject.dto.SubjectDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "subjects")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class Subject {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  Long subjectId;

  @Column(unique = true)
  String subjectName;

  Integer ectsPoints;

  SubjectDto dto() {

    return SubjectDto.builder()
        .subjectId(subjectId)
        .subjectName(subjectName)
        .ectsPoints(ectsPoints)
        .build();
  }

  SubjectCreatedEvent eventDto() {
    return new SubjectCreatedEvent(subjectId, subjectName);
  }
}
