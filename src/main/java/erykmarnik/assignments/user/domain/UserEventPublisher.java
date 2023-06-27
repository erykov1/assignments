package erykmarnik.assignments.user.domain;

import erykmarnik.assignments.user.dto.UserCreatedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserEventPublisher {

  ApplicationEventPublisher applicationEventPublisher;

  void notifyUserCreated(UserCreatedEvent userDto) {
    applicationEventPublisher.publishEvent(userDto);
  }
}
