package subjectAssignments.domain

import erykmarnik.assignments.subject.domain.SubjectConfiguration
import erykmarnik.assignments.subject.domain.SubjectFacade
import erykmarnik.assignments.subjectAssignment.domain.InMemorySubjectAssignedRepository
import erykmarnik.assignments.subjectAssignment.domain.InMemorySubjectAssignmentRepository
import erykmarnik.assignments.subjectAssignment.domain.InMemoryUserAssignedRepository
import erykmarnik.assignments.subjectAssignment.domain.SubjectAssignedConfig
import erykmarnik.assignments.subjectAssignment.domain.SubjectAssignedFacade
import erykmarnik.assignments.subjectAssignment.domain.SubjectAssignmentConfig
import erykmarnik.assignments.subjectAssignment.domain.SubjectAssignmentFacade
import erykmarnik.assignments.subjectAssignment.domain.UserAssignedConfig
import erykmarnik.assignments.subjectAssignment.domain.UserAssignedFacade
import erykmarnik.assignments.user.domain.UserConfiguration
import erykmarnik.assignments.user.domain.UserFacade
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

class SubjectAssignmentFacadeSpec extends Specification{
  ApplicationEventPublisher applicationEventPublisher = Mock()
  UserFacade userFacade = new UserConfiguration().userFacade(applicationEventPublisher)
  SubjectFacade subjectFacade = new SubjectConfiguration().subjectFacade(applicationEventPublisher)
  SubjectAssignedFacade subjectAssignedFacade = new SubjectAssignedConfig().subjectAssignedFacade()
  UserAssignedFacade userAssignedFacade = new UserAssignedConfig().userAssignedFacade()
  private InMemorySubjectAssignmentRepository subjectAssignmentRepository = new InMemorySubjectAssignmentRepository()
  private InMemorySubjectAssignedRepository subjectAssignedRepository = new InMemorySubjectAssignedRepository()
  private InMemoryUserAssignedRepository userAssignedRepository = new InMemoryUserAssignedRepository()
  SubjectAssignmentFacade subjectAssignmentFacade = new SubjectAssignmentConfig().subjectAssignmentFacade(
      subjectAssignedRepository, userAssignedRepository, subjectAssignmentRepository
  )
}
