package subject.domain

import erykmarnik.assignments.subject.dto.CreateSubjectDto
import erykmarnik.assignments.subject.dto.SubjectDto

class SubjectSpec extends SubjectFacadeSpec {

  def "should create subject"() {
    when: "creating subject"
      SubjectDto subject = subjectFacade.createSubject(new CreateSubjectDto(FIRST_SUBJECT_NAME, FIRST_SUBJECT_ECTS_POINTS))
    then: "subject is created"
      Optional<SubjectDto> subjectDtoResult = subjectFacade.getSubjectByName(FIRST_SUBJECT_NAME)
      subject.getSubjectId() == subjectDtoResult.get().getSubjectId()
      subject.getSubjectName() == subjectDtoResult.get().getSubjectName()
      subject.getEctsPoints() == subjectDtoResult.get().getEctsPoints()
  }

  def "should not create subject if it already exists"() {
    given: "there is a subject"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto(FIRST_SUBJECT_NAME, FIRST_SUBJECT_ECTS_POINTS))
    when: "subject with the same name is created"
      subjectFacade.createSubject(new CreateSubjectDto(FIRST_SUBJECT_NAME, FIRST_SUBJECT_ECTS_POINTS))
    then: "exception is thrown"
      thrown(Exception)
  }

  def "should get all subjects"() {
    given: "there are some subjects"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto(FIRST_SUBJECT_NAME, FIRST_SUBJECT_ECTS_POINTS))
      SubjectDto it = subjectFacade.createSubject(new CreateSubjectDto(SECOND_SUBJECT_NAME, SECOND_SUBJECT_ECTS_POINTS))
    when: "is asked for all subjects"
      List<SubjectDto> subjects = subjectFacade.getAllSubjects()
    then: "get all subjects"
      subjects.size() == 2
      subjects.contains(history)
      subjects.contains(it)
  }

  def "should get empty list if there are no subjects"() {
    when: "is asked for all subjects"
      List<SubjectDto> subjects = subjectFacade.getAllSubjects()
    then: "get empty list"
      subjects.isEmpty()
  }

  def "should find subject by name"() {
    given: "there is a subject"
      SubjectDto it = subjectFacade.createSubject(new CreateSubjectDto(SECOND_SUBJECT_NAME, SECOND_SUBJECT_ECTS_POINTS))
    when: "is asked for subject id"
      Optional<SubjectDto> subjectDto = subjectFacade.getSubjectByName(SECOND_SUBJECT_NAME)
    then: "find given subject"
      subjectDto.isPresent()
      it.getSubjectId() == subjectDto.get().getSubjectId()
      it.getSubjectName() == subjectDto.get().getSubjectName()
      it.getEctsPoints() == subjectDto.get().getEctsPoints()
  }

  def "should throw exception when subject is not find"() {
    when: "is asked for subject id"
      Optional<SubjectDto> subjectDto = subjectFacade.getSubjectByName(SECOND_SUBJECT_NAME)
    then: "exception is thrown"
      thrown(Exception)
  }

  def "should throw exception when try to find name as null"() {
    when: "is asked for subject id"
      Optional<SubjectDto> subjectDto = subjectFacade.getSubjectByName(null)
    then: "exception is thrown"
      thrown(Exception)
  }

  def "should get subjects with given ids"() {
    given: "there are some subjects"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto(FIRST_SUBJECT_NAME, FIRST_SUBJECT_ECTS_POINTS))
      SubjectDto it = subjectFacade.createSubject(new CreateSubjectDto(SECOND_SUBJECT_NAME, SECOND_SUBJECT_ECTS_POINTS))
    when: "is asked for ids this subjects"
      List<SubjectDto> subjects = subjectFacade.getAllSubjectsById([history.getSubjectId(), it.getSubjectId()])
    then: "get subjects ids"
      subjects.size() == 2
      subjects.containsAll([history, it])
  }

  def "should get only subjects that exist"() {
    given: "there is a subject"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto(FIRST_SUBJECT_NAME, FIRST_SUBJECT_ECTS_POINTS))
    when: "is asked for subjects with given ids"
      List<SubjectDto> subjects = subjectFacade.getAllSubjectsById([history.getSubjectId(), SECOND_SUBJECT_ID])
    then: "get only valid subjects"
      subjects.size() == 1
      subjects.containsAll([history])
  }

  def "should get subjects with valid id"() {
    given: "there is a subject"
      SubjectDto history = subjectFacade.createSubject(new CreateSubjectDto(FIRST_SUBJECT_NAME, FIRST_SUBJECT_ECTS_POINTS))
    when: "is asked for subjects with one valid id and one not"
      List<SubjectDto> subjects = subjectFacade.getAllSubjectsById([history.getSubjectId(), null])
    then: "get only valid subjects"
      subjects.size() == 1
      subjects.containsAll([history])
  }

  def "should get subject by id"() {
    given: "there is a subject"
      SubjectDto it = subjectFacade.createSubject(new CreateSubjectDto(SECOND_SUBJECT_NAME, SECOND_SUBJECT_ECTS_POINTS))
    when: "is asked for subject with given id"
      SubjectDto subjectDto = subjectFacade.getSubjectById(it.getSubjectId())
    then: "get subject"
      subjectDto.getSubjectId() == it.getSubjectId()
      subjectDto.getSubjectName() == it.getSubjectName()
      subjectDto.getEctsPoints() == it.getEctsPoints()
  }

  def "should throw exception when subject with given is not found"() {
    when: "is asked for subject with given id"
      SubjectDto subjectDto = subjectFacade.getSubjectById(FIRST_SUBJECT_ID)
    then: "exception is thrown"
      thrown(Exception)
  }

}
