package erykmarnik.assignments.user.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class InMemoryUserRepository implements UserRepository{

  private final Map<Long, User> values = new ConcurrentHashMap<>();

  @Override
  public List<User> findAllByUserIdIn(Collection<Long> userId) {

    return values
        .values()
        .stream()
        .filter( user ->
            userId.stream().anyMatch(id -> user.dto().getUserId().equals(id))
        )
        .collect(Collectors.toList());
  }

  @Override
  public Optional<User> findByUsername(String username) {

    return values
        .values()
        .stream()
        .filter(user -> user.dto().getUsername().equals(username))
        .findFirst();
  }

  @Override
  public void flush() {

  }

  @Override
  public <S extends User> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
    return null;
  }

  @Override
  public void deleteAllInBatch(Iterable<User> entities) {

  }

  @Override
  public void deleteAllByIdInBatch(Iterable<Long> longs) {

  }

  @Override
  public void deleteAllInBatch() {

  }

  @Override
  public User getOne(Long aLong) {
    return null;
  }

  @Override
  public User getById(Long aLong) {
    return null;
  }

  @Override
  public User getReferenceById(Long aLong) {
    return null;
  }

  @Override
  public <S extends User> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends User> List<S> findAll(Example<S> example) {
    return null;
  }

  @Override
  public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
    return null;
  }

  @Override
  public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends User> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends User> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public User save(User user) {

    if(user.dto().getUserId() == null || user.dto().getUserId() == 0L) {
      Long userId = new Random().nextLong();
      user = user.toBuilder()
          .userId(userId)
          .build();
    }
    values
        .put(user.dto().getUserId(), user);

    return user;
  }

  @Override
  public <S extends User> List<S> saveAll(Iterable<S> entities) {
    return null;
  }

  @Override
  public Optional<User> findById(Long aLong) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(Long aLong) {
    return false;
  }

  @Override
  public List<User> findAll() {

    return new ArrayList<>(values.values());
  }

  @Override
  public List<User> findAllById(Iterable<Long> longs) {
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
  public void delete(User entity) {

  }

  @Override
  public void deleteAllById(Iterable<? extends Long> longs) {

  }

  @Override
  public void deleteAll(Iterable<? extends User> entities) {

  }

  @Override
  public void deleteAll() {

  }

  @Override
  public List<User> findAll(Sort sort) {
    return null;
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    return null;
  }
}
