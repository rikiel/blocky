package eu.ba30.re.blocky.service.impl.db;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.cst.Category;

public interface CstCategoryRepository {
    @Nonnull
    List<Category> getAllCategories();

    @Nonnull
    Category getById(int categoryId);
}
