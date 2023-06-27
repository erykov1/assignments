package erykmarnik.assignments.subject.domain;

import erykmarnik.assignments.subject.dto.CreateSubjectDto;
import erykmarnik.assignments.subject.dto.SubjectDto;
import erykmarnik.assignments.subject.exception.SubjectAlreadyExists;
import erykmarnik.assignments.subject.exception.SubjectNotFoundException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubjectFacade {

  SubjectRepository subjectRepository;
  static Logger LOGGER = LoggerFactory.getLogger(SubjectFacade.class);
  SubjectEventPublisher subjectEventPublisher;

  public SubjectDto createSubject(CreateSubjectDto createSubjectDto) {

    subjectRepository.findBySubjectName(createSubjectDto.getSubjectName())
        .ifPresent(s -> {
          throw new SubjectAlreadyExists("Subject is already defined");
        });

    Subject saveSubject = Subject.builder()
        .subjectName(createSubjectDto.getSubjectName())
        .ectsPoints(createSubjectDto.getEctsPoints())
        .build();

    LOGGER.info("Subject: {} has been saved", saveSubject.dto().getSubjectName());
    subjectEventPublisher.notifySubjectCreated(saveSubject.eventDto());

    return subjectRepository.save(saveSubject).dto();
  }

  public List<SubjectDto> getAllSubjectsById(Collection<Long> subjectsIds) {

    LOGGER.info("Get all subjects by id : {}", subjectsIds);

    return subjectRepository.findAllBySubjectIdIn(subjectsIds)
        .stream()
        .map(Subject::dto)
        .collect(Collectors.toList());
  }

  public List<SubjectDto> getAllSubjects() {

    LOGGER.info("Get all subjects");

    return subjectRepository.findAll()
        .stream()
        .map(Subject::dto)
        .collect(Collectors.toList());
  }

  public SubjectDto getSubjectById(Long subjectId) {

    LOGGER.info("Get subject with id : {}", subjectId);

    return subjectRepository.findById(subjectId)
        .orElseThrow(() -> new SubjectNotFoundException("Subject doest not exist")).dto();
  }

  public Optional<SubjectDto> getSubjectByName(String subjectName) {

    LOGGER.info("Get subject with name : {}", subjectName);

    return Optional.ofNullable(subjectRepository.findBySubjectName(subjectName)
        .orElseThrow(() -> new SubjectNotFoundException("subject not found")).dto());
  }
}
