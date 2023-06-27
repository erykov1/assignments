package erykmarnik.assignments.subjectAssignment.domain;

import erykmarnik.assignments.subject.dto.SubjectCreatedEvent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectAssignedFacade {
  SubjectAssignedRepository subjectAssignedRepository;

  @EventListener
  public void onSubjectCreatedEvent(SubjectCreatedEvent subjectCreatedEvent) {
    SubjectAssigned subjectAssigned = SubjectAssigned.builder()
        .subjectId(subjectCreatedEvent.getSubjectId())
        .subjectName(subjectCreatedEvent.getSubjectName())
        .build();

    subjectAssignedRepository.save(subjectAssigned);
  }

}
