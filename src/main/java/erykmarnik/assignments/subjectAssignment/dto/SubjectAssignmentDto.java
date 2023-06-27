package erykmarnik.assignments.subjectAssignment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectAssignmentDto {

  Long assignmentId;

  Long subjectId;

  Set<Long> userIds;

  Instant assignmentConfirmedAt;

  Instant assignmentReportedAt;

  String assignmentStatus;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SubjectAssignmentDto other = (SubjectAssignmentDto) obj;
    return Objects.equals(assignmentId, other.assignmentId)
        && Objects.equals(subjectId, other.subjectId)
        && Objects.equals(userIds, other.userIds)
        && Objects.equals(assignmentConfirmedAt, other.assignmentConfirmedAt)
        && Objects.equals(assignmentReportedAt, other.assignmentReportedAt)
        && Objects.equals(assignmentStatus, other.assignmentStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(assignmentId, subjectId, userIds, assignmentConfirmedAt, assignmentReportedAt, assignmentStatus);
  }

}
