package erykmarnik.assignments.subjectAssignment.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InMemorySubjectAssignmentRepository implements SubjectAssignmentRepository {

  Map<Long, SubjectAssignment> values = new ConcurrentHashMap<>();

  @Override
  public void flush() {

  }

  @Override
  public <S extends SubjectAssignment> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends SubjectAssignment> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<SubjectAssignment> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public SubjectAssignment getOne(Long aLong) {
    return null;
  }

  @Override
  public SubjectAssignment getById(Long aLong) {
    return null;
  }

  @Override
  public SubjectAssignment getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends SubjectAssignment> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends SubjectAssignment> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends SubjectAssignment> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends SubjectAssignment> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends SubjectAssignment> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends SubjectAssignment> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends SubjectAssignment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public SubjectAssignment save(SubjectAssignment subjectAssignment) {

    if (subjectAssignment.dto().getAssignmentId() == null || subjectAssignment.dto().getAssignmentId() == 0L) {
      Long subjectAssignmentId = new Random().nextLong();
      subjectAssignment = subjectAssignment.toBuilder()
          .assignmentId(subjectAssignmentId)
          .build();
    }

    values.put(subjectAssignment.dto().getAssignmentId(), subjectAssignment);

    return subjectAssignment;
  }

  @Override
  public <S extends SubjectAssignment> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<SubjectAssignment> findById(Long assignmentId) {

    return values.values()
        .stream()
        .filter(subjectAssignment -> subjectAssignment.dto().getAssignmentId().equals(assignmentId))
        .findFirst();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<SubjectAssignment> findAll() {

    return new ArrayList<>(values.values());
  }

  @Override
  public List<SubjectAssignment> findAllById(Iterable<Long> longs) {
    return null;
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(Long aLong) {

  }

  @Override
  public void delete(SubjectAssignment entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends SubjectAssignment> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<SubjectAssignment> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<SubjectAssignment> findAll(Pageable pageable) {
    return null;
  }

  @Override
  public Optional<SubjectAssignment> findBySubjectId(Long subjectId) {

    return values.values()
        .stream()
        .filter(subjectAssignment -> subjectAssignment.dto().getSubjectId().equals(subjectId))
        .findFirst();
  }

  @Override
  public List<Long> findAllAssignedStudents(Collection<Long> userIds) {

    return values.values()
        .stream()
        .filter(subjectAssignment -> !Collections.disjoint(userIds, subjectAssignment.dto().getUserIds()))
        .flatMap(subjectAssignment -> subjectAssignment.dto().getUserIds().stream())
        .distinct()
        .collect(Collectors.toList());
  }

//  @Override
//  public List<Long> findAllAssignedStudentsIn(Collection<Long> userIds) {
//
//    return values.values()
//        .stream()
//        .filter(subjectAssignment -> !Collections.disjoint(userIds, subjectAssignment.dto().getUserIds()))
//        .flatMap(subjectAssignment -> subjectAssignment.dto().getUserIds().stream())
//        .distinct()
//        .collect(Collectors.toList());
//  }

  @Override
  public List<Long> findAllAssignedStudentsInGivenSubject(Long subjectId) {

    return values.values()
        .stream()
        .filter(subjectAssignment -> subjectAssignment.dto().getSubjectId().equals(subjectId))
        .flatMap(subjectAssignment -> subjectAssignment.dto().getUserIds().stream())
        .distinct()
        .collect(Collectors.toList());
  }
}
