package erykmarnik.assignments.subjectAssignment.dto;

import erykmarnik.assignments.user.dto.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Builder
public class UserAssignedDto {

  Long userId;

  UserRole userRole;
}
