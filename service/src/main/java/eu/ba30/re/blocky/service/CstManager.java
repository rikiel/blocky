package eu.ba30.re.blocky.service;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.cst.Category;

public interface CstManager {
    @Nonnull
    List<Category> getCategories();

    @Nonnull
    Category getCategory(int categoryId);
}
