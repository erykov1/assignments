package erykmarnik.assignments.subjectAssignment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectAssignedDto {

  Long subjectId;
  String subjectName;

}
