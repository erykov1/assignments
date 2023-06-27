package erykmarnik.assignments.user.domain;

import erykmarnik.assignments.user.dto.UserCreatedEvent;
import erykmarnik.assignments.user.dto.UserDto;
import erykmarnik.assignments.user.dto.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "users")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class User {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  Long userId;

  String name;

  String surname;

  String username;

  String password;

  @Enumerated(EnumType.STRING)
  UserRole userType;

  UserDto dto() {

    return UserDto.builder()
        .userId(userId)
        .name(name)
        .surname(surname)
        .username(username)
        .password(password)
        .userType(userType)
        .build();
  }

  UserCreatedEvent createdDto() {
    return new UserCreatedEvent(userId, userType);
  }
}
