package user.domain

import erykmarnik.assignments.user.domain.UserConfiguration
import erykmarnik.assignments.user.domain.UserFacade
import erykmarnik.assignments.user.dto.RegisterUserDto
import erykmarnik.assignments.user.dto.UserDto
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

class UserSpec extends Specification {
  private ApplicationEventPublisher applicationEventPublisher = Mock()
  private UserFacade userFacade = new UserConfiguration().userFacade(applicationEventPublisher)
  private static final FAKE_ID = 45L

  def "should register student"() {
    when: "student pass data (username, password, name, surname)"
      UserDto student = userFacade.registerStudent(new RegisterUserDto("John", "Smith",
            "john123", "johnPass"))
    then: "student is in system"
      Optional<UserDto> resultDto = userFacade.findByUsername(student.getUsername())
      resultDto.get().getUserId() == student.getUserId()
      resultDto.get().getUsername() == student.getUsername()
      resultDto.get().getPassword() == student.getPassword()
      resultDto.get().getName() == student.getName()
      resultDto.get().getSurname() == student.getSurname()
      resultDto.get().getUserType().toString() == "STUDENT"
  }

  def "should thrown exception when student pass already taken data"() {
    given: "there is already registered student"
      UserDto studentJohn = userFacade.registerStudent(new RegisterUserDto("John", "Smith",
          "smith123", "johnPass"))
    when: "another student try to register on the same username"
      UserDto studentMike = userFacade.registerStudent(new RegisterUserDto("Mike", "Smith",
          "smith123", "mikePass"))
    then: "exception is thrown"
      thrown(Exception)
  }

  def "should find all users with given ids"() {
    given: "there are some users in system"
      UserDto studentJohn = userFacade.registerStudent(new RegisterUserDto("John", "Smith",
          "john123", "johnPass"))
      UserDto studentMike = userFacade.registerStudent(new RegisterUserDto("Mike", "Smith",
          "mike123", "mikePass"))
    when: "system is asked for users with given ids"
      List<UserDto> resultDto = userFacade.getUsersById([studentJohn.getUserId(), studentMike.getUserId()])
    then: "returns all students with given ids"
      resultDto.size() == 2
      resultDto.containsAll([studentJohn, studentMike])
  }

  def "should return only one user when one id is valid and second not"() {
    given: "there are some users in system"
      UserDto studentJohn = userFacade.registerStudent(new RegisterUserDto("John", "Smith",
          "john123", "johnPass"))
      UserDto studentMike = userFacade.registerStudent(new RegisterUserDto("Mike", "Smith",
          "mike123", "mikePass"))
    when: "system is asked for users with given ids where one is not valid"
      List<UserDto> resultDto = userFacade.getUsersById([studentJohn.getUserId(), null])
    then: "return user with valid id"
      resultDto.size() == 1
      resultDto.get(0).getUserId() == studentJohn.getUserId()
      resultDto.get(0).getName() == studentJohn.getName()
      resultDto.get(0).getSurname() == studentJohn.getSurname()
      resultDto.get(0).getUsername() == studentJohn.getUsername()
      resultDto.get(0).getPassword() == studentJohn.getPassword()
      resultDto.get(0).getUserType().toString() == "STUDENT"
  }

  def "should return empty array when is asked for users that don't exist"() {
    when: "is asked for users that don't exist"
      List<UserDto> resultDto = userFacade.getUsersById([FAKE_ID])
    then: "returns empty array"
      resultDto.isEmpty()
  }

  def "should register user as teacher"() {
    when: "user is registered as teacher"
      UserDto teacher = userFacade.registerTeacher(new RegisterUserDto("Hannah", "Smith",
          "hSmith", "smith123"))
    then: "user has role teacher"
      Optional<UserDto> resultDto = userFacade.findByUsername(teacher.getUsername())
      resultDto.get().getUserId() == teacher.getUserId()
      resultDto.get().getName() == teacher.getName()
      resultDto.get().getSurname() == teacher.getSurname()
      resultDto.get().getPassword() == teacher.getPassword()
      resultDto.get().getUserType().toString() == "TEACHER"
  }
}
