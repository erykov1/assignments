package erykmarnik.assignments.subjectAssignment.domain;

import erykmarnik.assignments.user.dto.UserCreatedEvent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAssignedFacade {
  UserAssignedRepository userAssignedRepository;

  @EventListener
  public void onUserCreatedEvent(UserCreatedEvent event) {
    UserAssigned userAssigned = UserAssigned.builder()
        .userId(event.getUserId())
        .userRole(event.getUserRole())
        .build();

    userAssignedRepository.save(userAssigned);
  }
}
