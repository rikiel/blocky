package eu.ba30.re.blocky.service.impl.repository;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.cst.Category;

public interface CstCategoryRepository {
    /**
     * @return all categories stored in DB
     */
    @Nonnull
    List<Category> getCategoryList();

    /**
     * @param categoryId id of category
     * @return category
     */
    @Nonnull
    Category getCategoryById(int categoryId);
}
