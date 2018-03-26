package eu.ba30.re.blocky.service;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.cst.Category;

public interface CstManager {
    /**
     * @return all categories stored in DB
     */
    @Nonnull
    List<Category> getCategories();

    /**
     * @param categoryId id of category
     * @return category
     */
    @Nonnull
    Category getCategory(int categoryId);
}
