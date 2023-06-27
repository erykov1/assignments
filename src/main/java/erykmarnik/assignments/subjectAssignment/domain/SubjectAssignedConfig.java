package erykmarnik.assignments.subjectAssignment.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubjectAssignedConfig {

  public SubjectAssignedFacade subjectAssignedFacade() {
    return SubjectAssignedFacade.builder()
        .subjectAssignedRepository(new InMemorySubjectAssignedRepository())
        .build();
  }
  @Bean
  public SubjectAssignedFacade subjectAssignedFacade(SubjectAssignedRepository subjectAssignedRepository) {
    return SubjectAssignedFacade.builder()
        .subjectAssignedRepository(subjectAssignedRepository)
        .build();
  }
}
