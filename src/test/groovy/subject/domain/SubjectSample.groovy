package subject.domain

import groovy.transform.CompileStatic

@CompileStatic
trait SubjectSample {

  static Long FIRST_SUBJECT_ID = 1L
  static Long SECOND_SUBJECT_ID = 2L

  static String FIRST_SUBJECT_NAME = "History"
  static String SECOND_SUBJECT_NAME = "IT"

  static Long FIRST_TEACHER_ID = 1L
  static Long SECOND_TEACHER_ID = 2L

  static Integer FIRST_SUBJECT_ECTS_POINTS = 1
  static Integer SECOND_SUBJECT_ECTS_POINTS = 3

}