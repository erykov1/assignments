package erykmarnik.assignments.subject.infrastructure;

import erykmarnik.assignments.subject.domain.SubjectFacade;
import erykmarnik.assignments.subject.dto.CreateSubjectDto;
import erykmarnik.assignments.subject.dto.SubjectDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubjectController {

  SubjectFacade subjectFacade;

  @Autowired
  public SubjectController(SubjectFacade subjectFacade) {
    this.subjectFacade = subjectFacade;
  }

  @PostMapping("/createSubject")
  public ResponseEntity<SubjectDto> createSubject(@RequestBody CreateSubjectDto createSubjectDto) {

    return ResponseEntity.ok(subjectFacade.createSubject(createSubjectDto));
  }

  @GetMapping("/get/all")
  public ResponseEntity<List<SubjectDto>> getAllSubjects() {

    return ResponseEntity.ok(subjectFacade.getAllSubjects());
  }

  @GetMapping("/get/{subjectId}")
  public ResponseEntity<SubjectDto> getSubject(@PathVariable Long subjectId) {

    return ResponseEntity.ok(subjectFacade.getSubjectById(subjectId));
  }
}
