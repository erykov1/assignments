package erykmarnik.assignments.user.domain;

import erykmarnik.assignments.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

  UserRepository userRepository;

  @Autowired
  public UserDetailServiceImplementation(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDto user = userRepository.findByUsername(username).map(User::dto)
        .orElseThrow(() -> new UsernameNotFoundException("Can not find user with such username: " + username));

    return CustomUserDetailsService.builder()
        .userId(user.getUserId())
        .username(user.getUsername())
        .password("{noop}"+user.getPassword())
        .role(user.getUserType())
        .build();
  }
}
