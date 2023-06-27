package subject.domain

import erykmarnik.assignments.subject.domain.SubjectConfiguration
import erykmarnik.assignments.subject.domain.SubjectFacade
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

class SubjectFacadeSpec extends Specification implements SubjectSample {
  ApplicationEventPublisher applicationEventPublisher = Mock()

  SubjectFacade subjectFacade = new SubjectConfiguration().subjectFacade(applicationEventPublisher)
}
