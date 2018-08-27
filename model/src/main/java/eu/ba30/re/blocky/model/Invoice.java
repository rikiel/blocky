package eu.ba30.re.blocky.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.model.cst.Category;

public abstract class Invoice {
    public abstract Integer getId();

    public abstract void setId(Integer id);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract Category getCategory();

    public abstract void setCategory(Category category);

    public abstract String getDetails();

    public abstract void setDetails(String details);

    public abstract LocalDate getCreationDate();

    public abstract void setCreationDate(LocalDate creationDate);

    public abstract LocalDate getModificationDate();

    public abstract void setModificationDate(LocalDate modificationDate);

    @Nonnull
    public abstract List<Attachment> getAttachments();

    public abstract void setAttachments(@Nonnull List<Attachment> attachments);

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        final Invoice invoice = (Invoice) o;
        return Objects.equals(getId(), invoice.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("category", getCategory())
                .add("details", getDetails())
                .add("creationDate", getCreationDate())
                .add("modificationDate", getModificationDate())
                .add("attachments", getAttachments())
                .toString();
    }
}
