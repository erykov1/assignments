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

public class InMemoryUserAssignedRepository implements UserAssignedRepository {
  Map<Long, UserAssigned> values = new ConcurrentHashMap<>();

  @Override
  public List<UserAssigned> findAllStudentsByIdIn(Collection<Long> userIds) {
    return values.values()
        .stream()
        .filter(student -> student.dto().getUserRole().toString().equals("STUDENT"))
        .filter(student -> userIds.stream().anyMatch(id -> id.equals(student.dto().getUserId())))
        .collect(Collectors.toList());
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends UserAssigned> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends UserAssigned> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<UserAssigned> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public UserAssigned getOne(Long aLong) {
    return null;
  }

  @Override
  public UserAssigned getById(Long aLong) {
    return null;
  }

  @Override
  public UserAssigned getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends UserAssigned> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends UserAssigned> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends UserAssigned> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends UserAssigned> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends UserAssigned> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends UserAssigned> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends UserAssigned, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public UserAssigned save(UserAssigned userAssigned) {
    if(userAssigned.dto().getUserId() == null || userAssigned.dto().getUserId() == 0) {
      Long userId = new Random().nextLong();
      userAssigned = userAssigned.toBuilder()
          .userId(userId)
          .build();
    }
    values.put(userAssigned.dto().getUserId(), userAssigned);

    return userAssigned;
  }

  @Override
  public <S extends UserAssigned> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<UserAssigned> findById(Long aLong) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<UserAssigned> findAll() {
    return null;
  }

  @Override
  public List<UserAssigned> findAllById(Iterable<Long> longs) {
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
  public void delete(UserAssigned entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends UserAssigned> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<UserAssigned> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<UserAssigned> findAll(Pageable pageable) {
    return null;
  }
}
