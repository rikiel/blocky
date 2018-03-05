package eu.ba30.re.blocky;

import java.time.LocalDate;
import java.util.List;

public class Invoice {
    private String name;
    private String category;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    // TODO BLOCKY-3 Detaily poloziek - prilohy
    private List<String> attachements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public List<String> getAttachements() {
        return attachements;
    }

    public void setAttachements(List<String> attachements) {
        this.attachements = attachements;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                ", attachements=" + attachements +
                '}';
    }
}
