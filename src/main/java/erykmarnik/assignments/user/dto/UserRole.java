package erykmarnik.assignments.user.dto;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public enum UserRole {
  ADMIN,
  TEACHER,
  STUDENT
}
