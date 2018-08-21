package eu.ba30.re.blocky.view.overview.mvc.model;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.common.utils.Validate;

public class OperationResult {
    private final Result result;
    private final String message;

    public OperationResult(@Nonnull final Result result, @Nonnull final String message) {
        Validate.notNull(result, message);
        this.result = result;
        this.message = message;
    }

    @Nonnull
    public Result getResult() {
        return result;
    }

    @Nonnull
    public String getMessage() {
        return message;
    }

    public enum Result {
        SUCCESS,
        ERROR
    }
}
