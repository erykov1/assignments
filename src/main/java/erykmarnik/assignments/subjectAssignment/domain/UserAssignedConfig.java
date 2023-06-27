package erykmarnik.assignments.subjectAssignment.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAssignedConfig {

  public UserAssignedFacade userAssignedFacade() {
    return UserAssignedFacade.builder()
        .userAssignedRepository(new InMemoryUserAssignedRepository())
        .build();
  }

  @Bean
  UserAssignedFacade userAssignedFacade(UserAssignedRepository userAssignedRepository) {
    return UserAssignedFacade.builder()
        .userAssignedRepository(userAssignedRepository)
        .build();
  }
}
