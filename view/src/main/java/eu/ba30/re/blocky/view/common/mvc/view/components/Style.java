package eu.ba30.re.blocky.view.common.mvc.view.components;

import javax.annotation.Nonnull;

public enum Style {
    BUTTONS("buttons"),
    TABLE_ROW("table-row"),;

    @Nonnull
    private final String cssClass;

    Style(@Nonnull String cssClass) {
        this.cssClass = cssClass;
    }

    @Nonnull
    public String getCssClass() {
        return cssClass;
    }
}
