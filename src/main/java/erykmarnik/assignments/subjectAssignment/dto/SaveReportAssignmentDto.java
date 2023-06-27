package erykmarnik.assignments.subjectAssignment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SaveReportAssignmentDto {

  Long subjectId;

  Set<Long> userIds;

}
