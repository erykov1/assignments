package erykmarnik.assignments.subject.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@AllArgsConstructor
public class CreateSubjectDto {

  String subjectName;

  Integer ectsPoints;

}
