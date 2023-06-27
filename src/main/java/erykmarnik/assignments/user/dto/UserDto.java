package erykmarnik.assignments.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Builder
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDto {

  Long userId;

  String name;

  String surname;

  String username;

  String password;


  UserRole userType;

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    UserDto other = (UserDto) obj;
    return Objects.equals(userId, other.userId)
        && Objects.equals(name, other.name)
        && Objects.equals(surname, other.surname)
        && Objects.equals(username, other.username)
        && Objects.equals(password, other.password)
        && userType == other.userType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, name, surname, username, password, userType);
  }

}
