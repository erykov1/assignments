package erykmarnik.assignments.security;

import erykmarnik.assignments.security.request.LoginRequest;
import erykmarnik.assignments.security.service.TokenService;
import erykmarnik.assignments.user.domain.UserFacade;
import erykmarnik.assignments.user.dto.RegisterUserDto;
import erykmarnik.assignments.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController()
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

  UserFacade userFacade;
  TokenService tokenService;
  AuthenticationManager authenticationManager;
  BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public AuthController(UserFacade userFacade,
                        TokenService tokenService,
                        AuthenticationManager authenticationManager,
                        BCryptPasswordEncoder passwordEncoder) {
    this.userFacade = userFacade;
    this.tokenService = tokenService;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/student/register")
  public ResponseEntity<?> saveStudent(@RequestBody RegisterUserDto registerStudentDto) {
    return ResponseEntity.ok(userFacade.registerStudent(registerStudentDto));
  }

  @PostMapping("/teacher/register")
  public ResponseEntity<UserDto> saveTeacher(@RequestBody RegisterUserDto registerTeacherDto) {
    return ResponseEntity.ok(userFacade.registerTeacher(registerTeacherDto));
  }

  @PostMapping("/login")
  public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {

    Optional<UserDto> userDto = userFacade.findByUsername(loginRequest.username());
    if(userDto.isPresent()) {
      boolean matchPwd = passwordEncoder.matches(loginRequest.password(), userDto.get().getPassword());
      if(matchPwd) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.username(), userDto.get().getPassword())
        );
        String token = tokenService.generateToken(auth);
        return ResponseEntity.ok(token);
      }
    }

    return ResponseEntity.status(401).body("Bad credentials");
  }
}
