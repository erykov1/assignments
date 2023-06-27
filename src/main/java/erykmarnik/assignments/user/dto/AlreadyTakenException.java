package erykmarnik.assignments.user.dto;

public class AlreadyTakenException extends RuntimeException{

  public AlreadyTakenException(String msg) {
    super(msg);
  }
}
