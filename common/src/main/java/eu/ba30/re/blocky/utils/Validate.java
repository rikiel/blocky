package eu.ba30.re.blocky.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.exception.DatabaseException;

public class Validate {
    private Validate() {
        throw new UnsupportedOperationException();
    }

    public static void notNull(Object arg, String msg) {
        if (arg == null) {
            throw new NullPointerException(msg);
        }
    }

    public static void notNull(Object arg) {
        notNull(arg, "Argument should not be null");
    }

    public static void notNull(Object... args) {
        int i = 0;
        for (Object arg : args) {
            notNull(arg, "Argument #" + i + " is null");
            ++i;
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notNull(collection);
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("Collection should not be empty");
        }
    }

    public static void fail(Exception e) {
        throw new IllegalStateException(e);
    }

    public static void fail(String msg) {
        throw new IllegalStateException(msg);
    }

    public static void isNull(Object arg) {
        if (arg != null) {
            throw new IllegalArgumentException("Argument should be null");
        }
    }

    public static void isNull(Object... args) {
        for (Object arg : args) {
            isNull(arg);
        }
    }

    public static void isTrue(boolean expression, String msg) {
        if (!expression) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void equals(Object o1, Object o2, String msg) {
        if (!Objects.equals(o1, o2)) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void validateOneRowAffectedInDbCall(@Nonnull final int[] rowsAffected) {
        final boolean notOneAffected = Arrays
                .stream(rowsAffected)
                .anyMatch(value -> value != 1);
        if (notOneAffected) {
            throw new DatabaseException(String.format("Db failed to make change for some rows. Expected 1 affected row per attachment. Found %s",
                    Arrays.stream(rowsAffected).boxed().collect(Collectors.toList())));
        }
    }
}
