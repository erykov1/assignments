package erykmarnik.assignments;

import erykmarnik.assignments.security.RsaKeyProperties;
import erykmarnik.assignments.user.domain.UserFacade;
import erykmarnik.assignments.user.dto.RegisterUserDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan("erykmarnik.assignments.*")
public class AssignmentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentsApplication.class, args);
	}

}
