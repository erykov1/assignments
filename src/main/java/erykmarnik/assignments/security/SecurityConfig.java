package erykmarnik.assignments.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import erykmarnik.assignments.user.domain.UserDetailServiceImplementation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SecurityConfig {

  RsaKeyProperties rsaKeyProperties;
  UserDetailServiceImplementation userDetailServiceImplementation;
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public SecurityConfig(RsaKeyProperties rsaKeyProperties,
                        UserDetailServiceImplementation userDetailServiceImplementation,
                        BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.rsaKeyProperties = rsaKeyProperties;
    this.userDetailServiceImplementation = userDetailServiceImplementation;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailServiceImplementation);
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(bCryptPasswordEncoder);
    provider.setUserDetailsService(userDetailServiceImplementation);
    return provider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http
        .csrf().disable()
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/login").permitAll()
            .requestMatchers("/api/auth/student/register").permitAll()
            .requestMatchers("/api/auth/teacher/register").hasRole("ADMIN")
            .requestMatchers("/api/assignment/reportAssignment").hasAnyRole("ADMIN", "TEACHER")
            .requestMatchers("/api/assignment/acceptAssignment/**").hasRole("ADMIN")
            .requestMatchers("/api/subjects/createSubject").hasRole("ADMIN")
            .requestMatchers("/api/subjects/get/all").authenticated()
            .anyRequest()
              .authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
        .userDetailsService(userDetailServiceImplementation)
        .build();
  }

  @Bean
  JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey()).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(rsaKeyProperties.publicKey()).privateKey(rsaKeyProperties.privateKey()).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }
}
