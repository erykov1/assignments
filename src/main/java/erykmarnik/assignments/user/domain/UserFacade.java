package erykmarnik.assignments.user.domain;

import erykmarnik.assignments.user.dto.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Builder
public class UserFacade {

  UserRepository userRepository;
  static Logger LOGGER = LoggerFactory.getLogger(UserFacade.class);
  BCryptPasswordEncoder passwordEncoder;
  UserEventPublisher userEventPublisher;

  public UserDto registerStudent(RegisterUserDto studentRegistrationDto) {
    Optional<User> findUser = userRepository.findByUsername(studentRegistrationDto.getUsername());
    if(findUser.isPresent()) {
      throw new AlreadyTakenException("Username is already taken");
    }

    User saveStudent = User
        .builder()
        .name(studentRegistrationDto.getName())
        .surname(studentRegistrationDto.getSurname())
        .username(studentRegistrationDto.getUsername())
        .password(passwordEncoder.encode(studentRegistrationDto.getPassword()))
        .userType(UserRole.STUDENT)
        .build();

    LOGGER.info("Student has been registered");

    userEventPublisher.notifyUserCreated(saveStudent.createdDto());

    return userRepository.save(saveStudent).dto();
  }

  public UserDto registerTeacher(RegisterUserDto teacherRegistrationDto) {

    Optional<User> findUser = userRepository.findByUsername(teacherRegistrationDto.getUsername());
    if(findUser.isPresent()) {
      throw new AlreadyTakenException("Username is already taken");
    }

    User saveTeacher = User
        .builder()
        .name(teacherRegistrationDto.getName())
        .surname(teacherRegistrationDto.getSurname())
        .username(teacherRegistrationDto.getUsername())
        .password(passwordEncoder.encode(teacherRegistrationDto.getPassword()))
        .userType(UserRole.TEACHER)
        .build();

    LOGGER.info("Teacher has been registered");

    userEventPublisher.notifyUserCreated(saveTeacher.createdDto());

    return userRepository.save(saveTeacher).dto();
  }

  public List<UserDto> getUsersById(Collection<Long> ids) {

    return userRepository
        .findAllByUserIdIn(ids)
        .stream()
        .map(User::dto)
        .collect(Collectors.toList());
  }

  public Optional<UserDto> findByUsername(String username) {

    return userRepository.findByUsername(username)
        .map(User::dto);
  }

  public List<UserDto> getAllStudents() {

    List<User> students = userRepository.findAll()
        .stream()
        .filter(student -> student.dto().getUserType().toString().equals("STUDENT"))
        .collect(Collectors.toList());

    return students
        .stream()
        .map(User::dto)
        .toList();
  }

  public List<UserDto> getAllTeachers() {

    List<User> students = userRepository.findAll()
        .stream()
        .filter(student -> student.dto().getUserType().toString().equals("TEACHER"))
        .collect(Collectors.toList());

    return students
        .stream()
        .map(User::dto)
        .toList();
  }
}
