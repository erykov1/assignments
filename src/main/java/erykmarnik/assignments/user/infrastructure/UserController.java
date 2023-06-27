package erykmarnik.assignments.user.infrastructure;

import erykmarnik.assignments.user.domain.UserFacade;
import erykmarnik.assignments.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
  UserFacade userFacade;

  @Autowired
  public UserController(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @GetMapping("/all/students")
  public ResponseEntity<List<UserDto>> getAllStudents() {
    return ResponseEntity.ok(userFacade.getAllStudents());
  }

  @GetMapping("/all/teachers")
  public ResponseEntity<List<UserDto>> getAllTeachers() {
    return ResponseEntity.ok(userFacade.getAllTeachers());
  }
}
