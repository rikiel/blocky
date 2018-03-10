package eu.ba30.re.blocky.view.common.mvc.view;

import javax.annotation.Nonnull;

public enum Style {
    BUTTONS("buttons"),
    TABLE_ROW("table-row"),
    UPLOAD_FRAGMENT("upload-fragment"),
    UPLOAD("upload"),
    ATTACHMENT_PREVIEW("attachment-preview");

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
