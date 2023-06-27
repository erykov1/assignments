package erykmarnik.assignments.security.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TokenService {

  JwtEncoder jwtEncoder;
  static Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

  public TokenService(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  public String generateToken(Authentication authentication) {
    Instant now = Instant.now();
    String scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(1, ChronoUnit.HOURS))
        .subject(authentication.getName())
        .claim("scope", scope)
        .build();

    LOGGER.info("Token generated");

    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
