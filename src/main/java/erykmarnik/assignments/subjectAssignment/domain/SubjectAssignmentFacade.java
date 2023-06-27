package erykmarnik.assignments.subjectAssignment.domain;

import erykmarnik.assignments.subject.exception.SubjectNotFoundException;
import erykmarnik.assignments.subjectAssignment.exception.NotFoundException;
import erykmarnik.assignments.subjectAssignment.dto.SaveReportAssignmentDto;
import erykmarnik.assignments.subjectAssignment.dto.SubjectAssignmentDto;
import erykmarnik.assignments.subjectAssignment.dto.UpdateAssignmentStatusDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class SubjectAssignmentFacade {

  SubjectAssignmentRepository subjectAssignmentRepository;
  SubjectAssignedRepository subjectAssignedRepository;
  UserAssignedRepository userAssignedRepository;
  static Logger LOGGER = LoggerFactory.getLogger(SubjectAssignmentFacade.class);

  @Autowired
  SubjectAssignmentFacade(SubjectAssignmentRepository subjectAssignmentRepository,
                          SubjectAssignedRepository subjectAssignedRepository,
                          UserAssignedRepository userAssignedRepository) {
    this.subjectAssignmentRepository = subjectAssignmentRepository;
    this.userAssignedRepository = userAssignedRepository;
    this.subjectAssignedRepository = subjectAssignedRepository;
  }

  public UpdateAssignmentStatusDto updateAssignmentToAcceptedStatus(UpdateAssignmentStatusDto assignmentDto) {

    Optional<SubjectAssignment> findAssignment = subjectAssignmentRepository.findById(assignmentDto.getAssignmentId());

    if(findAssignment.isPresent()) {
      SubjectAssignment assignment = findAssignment.get();

      assignment = assignment.toBuilder()
          .assignmentConfirmedAt(Instant.now())
          .assignmentStatus(SubjectAssignment.AssignmentStatus.ACCEPTED)
          .build();

      LOGGER.info("Assignment with id: {} has been updated to ACCEPTED status", assignmentDto.getAssignmentId());

      return subjectAssignmentRepository.save(assignment).updateAssignmentStatusDto();
    }
    else {
      throw new NotFoundException("Assignment not found");
    }
  }

  public SubjectAssignmentDto reportAssignment(SaveReportAssignmentDto saveReportAssignmentDto) {
    subjectAssignedRepository.findById(saveReportAssignmentDto.getSubjectId()).orElseThrow(() ->
        new SubjectNotFoundException("Subject does not exist"));

    List<UserAssigned> students = userAssignedRepository.findAllStudentsByIdIn(saveReportAssignmentDto.getUserIds());

    if(saveReportAssignmentDto.getUserIds().isEmpty() || students.size() != saveReportAssignmentDto.getUserIds().size()) {
      throw new NotFoundException("There are no users to assign");
    }

    SubjectAssignment assignment = SubjectAssignment.builder()
        .subjectId(saveReportAssignmentDto.getSubjectId())
        .userIds(saveReportAssignmentDto.getUserIds())
        .assignmentReportedAt(Instant.now())
        .assignmentStatus(SubjectAssignment.AssignmentStatus.PENDING)
        .build();

    LOGGER.info("Assignment has been made and is waiting to accept");

    return subjectAssignmentRepository.save(assignment).dto();
  }

  public List<SubjectAssignmentDto> getAllAssignments() {

    LOGGER.info("Get all Assignments");

    return subjectAssignmentRepository.findAll()
        .stream()
        .map(SubjectAssignment::dto)
        .collect(Collectors.toList());
  }

  public Optional<SubjectAssignmentDto> findBySubjectAssignmentId(Long subjectId) {

    return subjectAssignmentRepository.findBySubjectId(subjectId)
        .map(SubjectAssignment::dto);
  }

  public List<Long> findAllAssignedStudentsIn(Collection<Long> userIds) {
    LOGGER.info("findAllAssignedStudentsIn called with userIds = {}", userIds);

    return new ArrayList<>(subjectAssignmentRepository.findAllAssignedStudents(userIds));
  }

  public List<Long> findAllAssignedStudentsInGivenSubject(Long subjectId) {
    LOGGER.info("findAllAssignedStudentsInGivenSubject called with subjectId = {} ", subjectId);
    subjectAssignedRepository.findById(subjectId).orElseThrow(() ->
        new SubjectNotFoundException("Subject does not exist"));
    return new ArrayList<>(subjectAssignmentRepository.findAllAssignedStudentsInGivenSubject(subjectId));
  }


}
