package erykmarnik.assignments.subject.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class InMemorySubjectRepository implements SubjectRepository {

  Map<Long, Subject> values = new ConcurrentHashMap<>();

  @Override
  public Optional<Subject> findBySubjectName(String subjectName) {

    return values.values()
        .stream()
        .filter(subject -> subject.dto().getSubjectName().equals(subjectName))
        .findFirst();
  }

  @Override
  public List<Subject> findAllBySubjectIdIn(Collection<Long> subjectsIds) {

    return values.values()
        .stream()
        .filter(subject -> subjectsIds.stream().anyMatch(id -> id.equals(subject.dto().getSubjectId())))
        .collect(Collectors.toList());
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends Subject> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends Subject> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<Subject> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public Subject getOne(Long aLong) {
    return null;
  }

  @Override
  public Subject getById(Long aLong) {
    return null;
  }

  @Override
  public Subject getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends Subject> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Subject> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends Subject> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends Subject> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Subject> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Subject> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends Subject, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public Subject save(Subject subject) {

    if(subject.dto().getSubjectId() == null || subject.dto().getSubjectId() == 0L) {
      Long subjectId = new Random().nextLong();

      subject = subject.toBuilder()
          .subjectId(subjectId)
          .build();
    }

    values.put(subject.dto().getSubjectId(), subject);

    return subject;
  }

  @Override
  public <S extends Subject> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<Subject> findById(Long subjectId) {

    return values.values()
        .stream()
        .filter(subject -> subject.dto().getSubjectId().equals(subjectId))
        .findFirst();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<Subject> findAll() {

    return new ArrayList<>(values.values());
  }

  @Override
  public List<Subject> findAllById(Iterable<Long> longs) {
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
  public void delete(Subject entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends Subject> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<Subject> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<Subject> findAll(Pageable pageable) {
    return null;
  }
}
