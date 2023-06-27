package erykmarnik.assignments.subjectAssignment.domain;

import erykmarnik.assignments.subjectAssignment.dto.UserAssignedDto;
import erykmarnik.assignments.user.dto.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Entity
@Table(name = "user_assigned")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserAssigned {

  @GeneratedValue(strategy = GenerationType.AUTO)
  @Id
  Long userId;

  UserRole userRole;

  @OneToMany
  Set<SubjectAssignment> subjectAssignments;

  UserAssignedDto dto() {

    return UserAssignedDto.builder()
        .userId(userId)
        .userRole(userRole)
        .build();
  }
}
