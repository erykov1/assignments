package erykmarnik.assignments.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCreatedEvent {

  Long userId;

  UserRole userRole;

  public UserCreatedEvent(Long userId, UserRole userRole) {
    this.userId = userId;
    this.userRole = userRole;
  }

}
