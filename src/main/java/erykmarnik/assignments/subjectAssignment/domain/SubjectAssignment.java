package erykmarnik.assignments.subjectAssignment.domain;

import erykmarnik.assignments.subjectAssignment.dto.SaveReportAssignmentDto;
import erykmarnik.assignments.subjectAssignment.dto.SubjectAssignmentDto;
import erykmarnik.assignments.subjectAssignment.dto.UpdateAssignmentStatusDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "subject_assignment")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class SubjectAssignment {

  enum AssignmentStatus {
    PENDING,
    ACCEPTED
  }

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  Long assignmentId;

  @Column(name = "subject_id")
  Long subjectId;

  @Column(name = "user_ids")
  Set<Long> userIds;

  @Column(name = "assignment_confirmed_at")
  Instant assignmentConfirmedAt;

  @Column(name = "assignment_reported_at")
  Instant assignmentReportedAt;

  @Column(name = "assignment_status")
  AssignmentStatus assignmentStatus;

  SubjectAssignmentDto dto() {

    return SubjectAssignmentDto.builder()
        .assignmentId(assignmentId)
        .subjectId(subjectId)
        .userIds(userIds)
        .assignmentConfirmedAt(assignmentConfirmedAt)
        .assignmentReportedAt(assignmentReportedAt)
        .assignmentStatus(assignmentStatus.name())
        .build();
  }

  SaveReportAssignmentDto saveReportAssignmentDto() {

    return SaveReportAssignmentDto.builder()
        .subjectId(subjectId)
        .userIds(userIds)
        .build();
  }

  UpdateAssignmentStatusDto updateAssignmentStatusDto() {

    return UpdateAssignmentStatusDto.builder()

        .assignmentId(assignmentId)
        .assignmentConfirmedAt(Instant.now())
        .build();
  }
}
