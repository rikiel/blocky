package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.impl.db.CstCategoryRepository;
import eu.ba30.re.blocky.utils.Validate;

@Service
public class CstCategoryRepositoryImpl implements CstCategoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Nonnull
    @Override
    public List<Category> getAllCategories() {
        final CriteriaQuery<Category> query = entityManager.getCriteriaBuilder().createQuery(Category.class);
        query.select(query.from(Category.class));
        return Validate.validateResult(entityManager.createQuery(query).getResultList());
    }

    @Nonnull
    @Override
    public Category getById(int categoryId) {
        return Validate.validateResult(entityManager.find(Category.class, categoryId));
    }
}
