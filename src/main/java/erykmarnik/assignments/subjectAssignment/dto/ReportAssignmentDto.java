package erykmarnik.assignments.subjectAssignment.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportAssignmentDto {

  Long subjectId;

  Set<Long> userIds;

}
