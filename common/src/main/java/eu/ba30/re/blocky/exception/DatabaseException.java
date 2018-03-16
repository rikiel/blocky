package eu.ba30.re.blocky.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String s) {
        super(s);
    }

    public DatabaseException(Throwable throwable) {
        super(throwable);
    }

    public DatabaseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
