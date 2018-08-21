package eu.ba30.re.blocky.view.common.mvc.view.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.AttachmentType;
import eu.ba30.re.blocky.model.cst.Category;

public class FormatterUtils {
    private FormatterUtils() {
    }

    @Nonnull
    public static String formatDate(@Nullable final LocalDate date) {
        return date == null
                ? ""
                : date.format(DateTimeFormatter.ofPattern("d. M. YYYY"));
    }

    @Nonnull
    public static String formatCategoryByName(@Nullable final Category category) {
        return category == null
                ? ""
                : category.getName();
    }

    @Nonnull
    public static String formatCategoryByNameAndDescription(@Nullable final Category category) {
        return category == null
                ? ""
                : String.format("%s (%s)", category.getName(), category.getDescription());
    }

    @Nonnull
    public static String formatAttachmentType(@Nullable final AttachmentType type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case IMAGE:
                return "Obrázok";
            case PDF:
                return "PDF dokument";
            case TEXT:
                return "Textový dokument";
            case UNKNOWN:
                return "Neznámy typ prílohy";
            default:
                Validate.fail("Not known type " + type);
                return null;
        }
    }
}
