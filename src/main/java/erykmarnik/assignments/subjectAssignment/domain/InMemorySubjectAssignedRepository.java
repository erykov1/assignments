package erykmarnik.assignments.subjectAssignment.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.function.Function;

public class InMemorySubjectAssignedRepository implements SubjectAssignedRepository{
  Map<Long, SubjectAssigned> values = new HashMap<>();

  @Override
  public void flush() {

  }

  @Override
  public <S extends SubjectAssigned> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends SubjectAssigned> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<SubjectAssigned> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public SubjectAssigned getOne(Long aLong) {
    return null;
  }

  @Override
  public SubjectAssigned getById(Long aLong) {
    return null;
  }

  @Override
  public SubjectAssigned getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends SubjectAssigned> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends SubjectAssigned> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends SubjectAssigned> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends SubjectAssigned> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends SubjectAssigned> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends SubjectAssigned> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends SubjectAssigned, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public SubjectAssigned save(SubjectAssigned subjectAssigned) {
    Long subjectId = subjectAssigned.dto().getSubjectId();
    if (subjectId == null || subjectId == 0L || values.containsKey(subjectId)) {
      subjectId = new Random().nextLong();
      subjectAssigned = subjectAssigned.toBuilder()
          .subjectId(subjectId)
          .build();
    }
    values.put(subjectId, subjectAssigned);

    return subjectAssigned;
  }


  @Override
  public <S extends SubjectAssigned> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<SubjectAssigned> findById(Long subjectId) {
    return values.values()
        .stream()
        .filter(subjectAssigned -> subjectAssigned.dto().getSubjectId().equals(subjectId))
        .findFirst();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<SubjectAssigned> findAll() {
    return null;
  }

  @Override
  public List<SubjectAssigned> findAllById(Iterable<Long> longs) {
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
  public void delete(SubjectAssigned entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends SubjectAssigned> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<SubjectAssigned> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<SubjectAssigned> findAll(Pageable pageable) {
    return null;
  }
}
