package eu.ba30.re.blocky.service.mybatis.impl.db;

import java.util.List;

import javax.annotation.Nonnull;

import eu.ba30.re.blocky.model.cst.Category;

public interface MyBatisCstCategoryRepository {
    /**
     * @return all categories stored in DB
     */
    @Nonnull
    List<Category> getAllCategories();

    /**
     * @param categoryId id of category
     * @return category
     */
    @Nonnull
    Category getById(int categoryId);
}
