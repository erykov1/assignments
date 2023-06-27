package erykmarnik.assignments.subjectAssignment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAssignmentStatusDto {

  Long assignmentId;

  Instant assignmentConfirmedAt;

}
