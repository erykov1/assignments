package subjectAssignments.domain

import erykmarnik.assignments.subject.domain.SubjectConfiguration
import erykmarnik.assignments.subject.domain.SubjectFacade
import erykmarnik.assignments.subject.dto.CreateSubjectDto
import erykmarnik.assignments.subject.dto.SubjectCreatedEvent
import erykmarnik.assignments.subject.dto.SubjectDto
import erykmarnik.assignments.subjectAssignment.domain.InMemorySubjectAssignedRepository
import erykmarnik.assignments.subjectAssignment.domain.InMemorySubjectAssignmentRepository
import erykmarnik.assignments.subjectAssignment.domain.InMemoryUserAssignedRepository
import erykmarnik.assignments.subjectAssignment.domain.SubjectAssignedConfig
import erykmarnik.assignments.subjectAssignment.domain.SubjectAssignedFacade
import erykmarnik.assignments.subjectAssignment.domain.SubjectAssignmentConfig
import erykmarnik.assignments.subjectAssignment.domain.SubjectAssignmentFacade
import erykmarnik.assignments.subjectAssignment.domain.UserAssignedConfig
import erykmarnik.assignments.subjectAssignment.domain.UserAssignedFacade
import erykmarnik.assignments.subjectAssignment.dto.SaveReportAssignmentDto
import erykmarnik.assignments.subjectAssignment.dto.SubjectAssignmentDto
import erykmarnik.assignments.subjectAssignment.dto.UpdateAssignmentStatusDto
import erykmarnik.assignments.user.domain.UserConfiguration
import erykmarnik.assignments.user.domain.UserFacade
import erykmarnik.assignments.user.dto.RegisterUserDto
import erykmarnik.assignments.user.dto.UserCreatedEvent
import erykmarnik.assignments.user.dto.UserDto
import org.springframework.context.ApplicationEventPublisher

import java.time.Duration
import spock.lang.Specification

import java.time.Instant

class SubjectAssignmentSpec extends Specification implements SubjectAssignmentSample {
  ApplicationEventPublisher applicationEventPublisher = Mock()
  InMemorySubjectAssignedRepository subjectAssignedRepository = new InMemorySubjectAssignedRepository()
  InMemoryUserAssignedRepository userAssignedRepository = new InMemoryUserAssignedRepository()
  InMemorySubjectAssignmentRepository subjectAssignmentRepository = new InMemorySubjectAssignmentRepository()

  UserFacade userFacade = new UserConfiguration().userFacade(applicationEventPublisher)
  SubjectFacade subjectFacade = new SubjectConfiguration().subjectFacade(applicationEventPublisher)
  SubjectAssignedFacade subjectAssignedFacade = new SubjectAssignedConfig().subjectAssignedFacade(subjectAssignedRepository)
  UserAssignedFacade userAssignedFacade = new UserAssignedConfig().userAssignedFacade(userAssignedRepository)
  SubjectAssignmentFacade subjectAssignmentFacade = new SubjectAssignmentConfig().subjectAssignmentFacade(
      subjectAssignedRepository, userAssignedRepository, subjectAssignmentRepository
  )

  def "should create subject assignment"() {
    given: "there are subject and users"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto("History", 1))
      UserDto userMarryDto = userFacade.registerStudent(new RegisterUserDto("Marry", "Doe", "marry123", "123"))
      UserDto userJaneDto = userFacade.registerStudent(new RegisterUserDto("Jane", "Doe", "jane1234", "1234"))
    and: "the subject is saved correctly"
      subjectAssignedFacade.onSubjectCreatedEvent(new SubjectCreatedEvent(history.getSubjectId(), history.getSubjectName()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMarryDto.getUserId(), userMarryDto.getUserType()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userJaneDto.getUserId(), userJaneDto.getUserType()))
    when: "the assignment is saved"
      SubjectAssignmentDto saveAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          history.getSubjectId(), [userMarryDto.getUserId(), userJaneDto.getUserId()] as Set<Long>
      ))
    then: "check assignment"
      Optional<SubjectAssignmentDto> resultDto = subjectAssignmentFacade.findBySubjectAssignmentId(
          saveAssignmentDto.getSubjectId())
      resultDto.get().getAssignmentId() == saveAssignmentDto.getAssignmentId()
      resultDto.get().getUserIds() == saveAssignmentDto.getUserIds()
      resultDto.get().getSubjectId() == saveAssignmentDto.getSubjectId()
      resultDto.get().getAssignmentStatus() == saveAssignmentDto.getAssignmentStatus()
      resultDto.get().getAssignmentReportedAt() == saveAssignmentDto.getAssignmentReportedAt()
  }

  def "should throw exception when there are no users to assign"() {
    when: "there is a try to save assignment"
      SubjectAssignmentDto saveAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          ASSIGNMENT_ID, null
      ))
    then: "exception is thrown"
      thrown(Exception)
  }

  def "should not update assignment status if the assignment is not valid"() {
    Instant now = Instant.now()
    given: "there are some students and subject"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto("History", 1))
      UserDto userMarryDto = userFacade.registerStudent(new RegisterUserDto("Marry", "Doe", "marry123", "123"))
      UserDto userJaneDto = userFacade.registerStudent(new RegisterUserDto("Jane", "Doe", "jane1234", "1234"))
    and: "assignment has been made and is waiting for accept"
      subjectAssignedFacade.onSubjectCreatedEvent(new SubjectCreatedEvent(history.getSubjectId(), history.getSubjectName()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMarryDto.getUserId(), userMarryDto.getUserType()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userJaneDto.getUserId(), userJaneDto.getUserType()))
      SubjectAssignmentDto saveAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          history.getSubjectId() ,[userMarryDto.getUserId(), userJaneDto.getUserId()] as Set<Long>
      ))
      UpdateAssignmentStatusDto updateAssignmentStatusDto = new UpdateAssignmentStatusDto
          (null, now)
    when: "assignment is updated to accept"
      subjectAssignmentFacade.updateAssignmentToAcceptedStatus(updateAssignmentStatusDto)
    then: "exception is thrown"
      thrown(Exception)
  }

  def "should update status to ACCEPTED"() {
    Instant now = Instant.now()
    given: "there are some existing students and subject"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto("History", 1))
      UserDto userMarryDto = userFacade.registerStudent(new RegisterUserDto("Marry", "Doe", "marry123", "123"))
      UserDto userJaneDto = userFacade.registerStudent(new RegisterUserDto("Jane", "Doe", "jane1234", "1234"))
      subjectAssignedFacade.onSubjectCreatedEvent(new SubjectCreatedEvent(history.getSubjectId(), history.getSubjectName()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMarryDto.getUserId(), userMarryDto.getUserType()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userJaneDto.getUserId(), userJaneDto.getUserType()))
    and: "is waiting to be accepted"
      SubjectAssignmentDto saveAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          history.getSubjectId() ,[userMarryDto.getUserId(), userJaneDto.getUserId()] as Set<Long>
      ))
      UpdateAssignmentStatusDto updateAssignmentStatusDto = new UpdateAssignmentStatusDto
          (saveAssignmentDto.getAssignmentId(), now)
    when: "assignment is updated to accept"
      subjectAssignmentFacade.updateAssignmentToAcceptedStatus(updateAssignmentStatusDto)
    then: "assignment has been updated"
      Optional<SubjectAssignmentDto> resultDto =
          subjectAssignmentFacade.findBySubjectAssignmentId(saveAssignmentDto.getSubjectId())
      resultDto.get().getAssignmentId() == updateAssignmentStatusDto.getAssignmentId()
      Math.abs(Duration.between(resultDto.get().getAssignmentConfirmedAt(), updateAssignmentStatusDto.getAssignmentConfirmedAt())
          .toSeconds()) <= 1
  }

  def "should get all assignments"() {
    given: "there are some existing students and subject"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto("History", 1))
      UserDto userMarryDto = userFacade.registerStudent(new RegisterUserDto("Marry", "Doe", "marry123", "123"))
      UserDto userJaneDto = userFacade.registerStudent(new RegisterUserDto("Jane", "Doe", "jane1234", "1234"))
      UserDto userMikeDto = userFacade.registerStudent(new RegisterUserDto("Mike", "Doe", "mike1234", "1234"))
      subjectAssignedFacade.onSubjectCreatedEvent(new SubjectCreatedEvent(history.getSubjectId(), history.getSubjectName()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMarryDto.getUserId(), userMarryDto.getUserType()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userJaneDto.getUserId(), userJaneDto.getUserType()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMikeDto.getUserId(), userMikeDto.getUserType()))
    and: "there are assignments"
      SubjectAssignmentDto saveAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          history.getSubjectId() ,[userMarryDto.getUserId(), userJaneDto.getUserId()] as Set<Long>
      ))
      SubjectAssignmentDto saveSecondAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          history.getSubjectId() ,[userMikeDto.getUserId()] as Set<Long>
      ))
    when: "is asked for all assignments"
      List<SubjectAssignmentDto> resultDto = subjectAssignmentFacade.getAllAssignments()
    then: "get all assignments"
      resultDto.size() == 2
      resultDto.containsAll([saveAssignmentDto, saveSecondAssignmentDto])
  }

  def "should not get assignments if there aren't any"() {
    when: "is asked for assignments"
      List<SubjectAssignmentDto> resultDto = subjectAssignmentFacade.getAllAssignments()
    then: "return empty list of assignments"
      resultDto.isEmpty()
  }

  def "should get assigned students to subject"() {
    given: "there are some existing students and subject"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto("History", 1))
      UserDto userMarryDto = userFacade.registerStudent(new RegisterUserDto("Marry", "Doe", "marry123", "123"))
      subjectAssignedFacade.onSubjectCreatedEvent(new SubjectCreatedEvent(history.getSubjectId(), history.getSubjectName()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMarryDto.getUserId(), userMarryDto.getUserType()))
    and: "there are some assignments"
      SubjectAssignmentDto saveAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          history.getSubjectId() ,[userMarryDto.getUserId()] as Set<Long>
      ))
    when: "is asked for students that are assigned"
      List<Long> userIds = subjectAssignmentFacade.findAllAssignedStudentsIn([userMarryDto.getUserId(), 2L, 3L])
    then: "get all students that are assigned to the subject"
      userIds.size() == 1
      userIds.containsAll([userMarryDto.getUserId()])
  }

  def "should be empty if is asked for students that are not assigned to the subject"() {
    when: "is asked for students that are not assigned"
      List<Long> userIds = subjectAssignmentFacade.findAllAssignedStudentsIn([1L, 2L, 3L])
    then: "get empty list"
      userIds.isEmpty()
  }

  def "should be empty if is asked with invalid data"() {
    when: "is asked for students with invalid data"
      List<Long> userIds = subjectAssignmentFacade.findAllAssignedStudentsIn([null, null])
    then: "get be empty list"
      userIds.isEmpty()
  }

  def "should return only students with valid data"() {
    given: "there are some existing students and subject"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto("History", 1))
      UserDto userMarryDto = userFacade.registerStudent(new RegisterUserDto("Marry", "Doe", "marry123", "123"))
      subjectAssignedFacade.onSubjectCreatedEvent(new SubjectCreatedEvent(history.getSubjectId(), history.getSubjectName()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMarryDto.getUserId(), userMarryDto.getUserType()))
    and: "there are some assignments"
      SubjectAssignmentDto saveAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          history.getSubjectId() ,[userMarryDto.getUserId()] as Set<Long>
      ))
    when: "is asked for students with valid and invalid data"
      List<Long> userIds = subjectAssignmentFacade.findAllAssignedStudentsIn([null, null, userMarryDto.getUserId(), 2L, 3L])
    then: "get all students that are assigned to the subject"
      userIds.size() == 1
      userIds.containsAll([userMarryDto.getUserId()])
  }

  def "should get all students from given subject id"() {
    given: "there are some existing students and subject"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto("History", 1))
      UserDto userMarryDto = userFacade.registerStudent(new RegisterUserDto("Marry", "Doe", "marry123", "123"))
      UserDto userJaneDto = userFacade.registerStudent(new RegisterUserDto("Jane", "Doe", "jane1234", "1234"))
      UserDto userMikeDto = userFacade.registerStudent(new RegisterUserDto("Mike", "Doe", "mike1234", "1234"))
      subjectAssignedFacade.onSubjectCreatedEvent(new SubjectCreatedEvent(history.getSubjectId(), history.getSubjectName()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMarryDto.getUserId(), userMarryDto.getUserType()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userJaneDto.getUserId(), userJaneDto.getUserType()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMikeDto.getUserId(), userMikeDto.getUserType()))
    and: "there are some assignments"
      SubjectAssignmentDto saveAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          history.getSubjectId() ,[userMarryDto.getUserId(), userJaneDto.getUserId(), userMikeDto.getUserId()] as Set<Long>
      ))
    when: "is asked for students from given subject"
      List<Long> userIds = subjectAssignmentFacade.findAllAssignedStudentsInGivenSubject(saveAssignmentDto.getSubjectId())
    then: "get all assigned users to given subject"
      userIds.size() == 3
      userIds.containsAll([userMikeDto.getUserId(), userJaneDto.getUserId(), userMarryDto.getUserId()])
  }

  def "should thrown exception if given subject not exists"() {
    when: "is asked for students from given subject"
      List<Long> userIds = subjectAssignmentFacade.findAllAssignedStudentsInGivenSubject(SUBJECT_ID)
    then: "throw exception"
      thrown(Exception)
  }

  def "should get students only from given subject"() {
    given: "there are some existing students and subjects"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto("History", 1))
      SubjectDto it = subjectFacade.createSubject(new CreateSubjectDto("IT", 3))
      UserDto userMarryDto = userFacade.registerStudent(new RegisterUserDto("Marry", "Doe", "marry123", "123"))
      UserDto userJaneDto = userFacade.registerStudent(new RegisterUserDto("Jane", "Doe", "jane1234", "1234"))
      UserDto userMikeDto = userFacade.registerStudent(new RegisterUserDto("Mike", "Doe", "mike1234", "1234"))
      subjectAssignedFacade.onSubjectCreatedEvent(new SubjectCreatedEvent(history.getSubjectId(), history.getSubjectName()))
      subjectAssignedFacade.onSubjectCreatedEvent(new SubjectCreatedEvent(it.getSubjectId(), it.getSubjectName()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMarryDto.getUserId(), userMarryDto.getUserType()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userJaneDto.getUserId(), userJaneDto.getUserType()))
      userAssignedFacade.onUserCreatedEvent(new UserCreatedEvent(userMikeDto.getUserId(), userMikeDto.getUserType()))
    and: "there are some more assignments"
      SubjectAssignmentDto saveAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          history.getSubjectId() ,[userMarryDto.getUserId(), userMikeDto.getUserId()] as Set<Long>
      ))
      SubjectAssignmentDto saveSecondAssignmentDto = subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          it.getSubjectId() ,[userJaneDto.getUserId()] as Set<Long>
      ))
    when: "is asked for students from given subject"
      List<Long> userIds = subjectAssignmentFacade.findAllAssignedStudentsInGivenSubject(saveAssignmentDto.getSubjectId())
    then: "get students only from given subject"
      userIds.size() == 2
      userIds.containsAll([userMarryDto.getUserId(), userMikeDto.getUserId()])
  }

  def "should throw exception if try to assign students that do not exist"() {
    when: "make assignment with not existing students"
      subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          SUBJECT_ID ,[1L, 2L] as Set<Long>))
    then: "exception is thrown"
      thrown(Exception)
  }

  def "should thrown exception if try to assign subject that does not exist"() {
    when: "make assignment with not existing subject"
      subjectAssignmentFacade.reportAssignment(new SaveReportAssignmentDto(
          SUBJECT_ID ,[1L, 2L] as Set<Long>))
    then: "exception is thrown"
      thrown(Exception)
  }
}
