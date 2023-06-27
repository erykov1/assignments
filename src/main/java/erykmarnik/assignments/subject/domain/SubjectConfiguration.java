package erykmarnik.assignments.subject.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubjectConfiguration {

  public SubjectFacade subjectFacade(ApplicationEventPublisher applicationEventPublisher) {

    return SubjectFacade.builder()
        .subjectRepository(new InMemorySubjectRepository())
        .subjectEventPublisher(new SubjectEventPublisher(applicationEventPublisher))
        .build();
  }

  @Bean
  SubjectFacade subjectFacade(SubjectRepository subjectRepository, ApplicationEventPublisher applicationEventPublisher) {

    return SubjectFacade.builder()
        .subjectRepository(subjectRepository)
        .subjectEventPublisher(new SubjectEventPublisher(applicationEventPublisher))
        .build();
  }
}
