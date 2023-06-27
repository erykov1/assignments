package erykmarnik.assignments.subject.domain;

import erykmarnik.assignments.subject.dto.SubjectCreatedEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectEventPublisher {

  ApplicationEventPublisher applicationEventPublisher;

  void notifySubjectCreated(SubjectCreatedEvent subjectEventDto) {
    applicationEventPublisher.publishEvent(subjectEventDto);
  }
}
