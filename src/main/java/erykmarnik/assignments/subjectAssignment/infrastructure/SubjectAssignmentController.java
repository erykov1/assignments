package erykmarnik.assignments.subjectAssignment.infrastructure;

import erykmarnik.assignments.subjectAssignment.domain.SubjectAssignmentFacade;
import erykmarnik.assignments.subjectAssignment.dto.ReportAssignmentDto;
import erykmarnik.assignments.subjectAssignment.dto.SaveReportAssignmentDto;
import erykmarnik.assignments.subjectAssignment.dto.SubjectAssignmentDto;
import erykmarnik.assignments.subjectAssignment.dto.UpdateAssignmentStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment/")
public class SubjectAssignmentController {

  SubjectAssignmentFacade subjectAssignmentFacade;

  @Autowired
  public SubjectAssignmentController(SubjectAssignmentFacade subjectAssignmentFacade) {
    this.subjectAssignmentFacade = subjectAssignmentFacade;
  }

  @PostMapping("reportAssignment")
  public ResponseEntity<SubjectAssignmentDto> reportAssignment(@RequestBody ReportAssignmentDto
                                                                        reportAssignmentDto) {

    SaveReportAssignmentDto saveReportAssignmentDto = SaveReportAssignmentDto.builder()
        .subjectId(reportAssignmentDto.getSubjectId())
        .userIds(reportAssignmentDto.getUserIds())
        .build();

    return ResponseEntity.ok(subjectAssignmentFacade.reportAssignment(saveReportAssignmentDto));
  }

  @PutMapping("acceptAssignment/{assignmentId}")
  public ResponseEntity<UpdateAssignmentStatusDto> acceptAssignment(@PathVariable Long assignmentId) {

    UpdateAssignmentStatusDto updateAssignmentStatusDto = UpdateAssignmentStatusDto.builder()
        .assignmentId(assignmentId)
        .build();

    return ResponseEntity.ok(subjectAssignmentFacade.updateAssignmentToAcceptedStatus(updateAssignmentStatusDto));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/get/allAssignedStudents/{subjectId}")
  public ResponseEntity<List<Long>> getAllAssignedStudents(@PathVariable Long subjectId) {

    return ResponseEntity.ok(subjectAssignmentFacade.findAllAssignedStudentsInGivenSubject(subjectId));
  }
}
