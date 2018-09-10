package eu.ba30.re.blocky.model.cst;

import java.util.Objects;

import com.google.common.base.MoreObjects;

import eu.ba30.re.blocky.model.HasId;

public abstract class Category implements HasId {
    @Override
    public abstract Integer getId();

    public abstract void setId(Integer id);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getDescription();

    public abstract void setDescription(String description);

    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        final Category category = (Category) o;
        return Objects.equals(getId(), category.getId());
    }

    public final int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("name", getName())
                .add("description", getDescription())
                .toString();
    }
}
