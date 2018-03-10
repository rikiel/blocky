package eu.ba30.re.blocky.service;

import eu.ba30.re.blocky.model.cst.Category;

import javax.annotation.Nonnull;
import java.util.List;

public interface CstManager {
    @Nonnull
    List<Category> getCategories();
}
