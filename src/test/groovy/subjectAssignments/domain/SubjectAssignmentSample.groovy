package subjectAssignments.domain

import erykmarnik.assignments.subject.dto.SubjectDto
import erykmarnik.assignments.subjectAssignment.dto.SaveReportAssignmentDto

trait SubjectAssignmentSample {
  final static Long ASSIGNMENT_ID = 1L
  final static Long SUBJECT_ID = 1L
  final static Long SECOND_SUBJECT_ID = 2L

  final static SaveReportAssignmentDto SAVE_ASSIGNMENT_DTO = new SaveReportAssignmentDto(
      ASSIGNMENT_ID, [1L, 2L] as Set<Long>
  )

  final static SubjectDto HISTORY = new SubjectDto(1L, "History", 1)
  final static SubjectDto IT = new SubjectDto(2L, "IT", 3)


}