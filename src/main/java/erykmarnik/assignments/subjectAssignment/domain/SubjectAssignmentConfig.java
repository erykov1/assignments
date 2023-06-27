package erykmarnik.assignments.subjectAssignment.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubjectAssignmentConfig {

  public SubjectAssignmentFacade subjectAssignmentFacade(InMemorySubjectAssignedRepository inMemorySubjectAssignedRepository,
                                                         InMemoryUserAssignedRepository inMemoryUserAssignedRepository,
                                                         InMemorySubjectAssignmentRepository inMemorySubjectAssignmentRepository) {

    return SubjectAssignmentFacade.builder()
        .subjectAssignmentRepository(inMemorySubjectAssignmentRepository)
        .userAssignedRepository(inMemoryUserAssignedRepository)
        .subjectAssignedRepository(inMemorySubjectAssignedRepository)
        .build();
  }
  @Bean
  public SubjectAssignmentFacade subjectAssignmentFacade(SubjectAssignmentRepository subjectAssignmentRepository,
                                                         UserAssignedRepository userAssignedRepository,
                                                         SubjectAssignedRepository subjectAssignedRepository) {

    return SubjectAssignmentFacade.builder()
        .subjectAssignmentRepository(subjectAssignmentRepository)
        .userAssignedRepository(userAssignedRepository)
        .subjectAssignedRepository(subjectAssignedRepository)
        .build();
  }
}
