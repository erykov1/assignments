package erykmarnik.assignments.user.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public
class UserConfiguration {

  public UserFacade userFacade(ApplicationEventPublisher applicationEventPublisher) {

    return UserFacade.builder()
        .userRepository(new InMemoryUserRepository())
        .passwordEncoder(new BCryptPasswordEncoder())
        .userEventPublisher(new UserEventPublisher(applicationEventPublisher))
        .build();
  }

  @Bean
  public UserFacade userFacade(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                               ApplicationEventPublisher eventPublisher) {

    return UserFacade.builder()
        .userRepository(userRepository)
        .passwordEncoder(passwordEncoder)
        .userEventPublisher(new UserEventPublisher(eventPublisher))
        .build();
  }
}
