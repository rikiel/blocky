package eu.ba30.re.blocky.view;

import javax.annotation.Nonnull;

public enum ApplicationViewName {
    OVERVIEW(""),
    BULK_DELETE("bulkDelete"),
    CREATE("create"),
    ;

    @Nonnull
    private final String viewName;

    ApplicationViewName(@Nonnull String viewName) {
        this.viewName = viewName;
    }

    @Nonnull
    public String getViewName() {
        return viewName;
    }
}
