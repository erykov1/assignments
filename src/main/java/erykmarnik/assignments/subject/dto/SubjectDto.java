package erykmarnik.assignments.subject.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@AllArgsConstructor
public class SubjectDto {

  Long subjectId;

  String subjectName;

  Integer ectsPoints;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SubjectDto other = (SubjectDto) obj;
    return Objects.equals(subjectId, other.subjectId) &&
        Objects.equals(subjectName, other.subjectName) &&
        ectsPoints == other.ectsPoints;
  }

  @Override
  public int hashCode() {
    return Objects.hash(subjectId, subjectName, ectsPoints);
  }

}
