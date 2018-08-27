package eu.ba30.re.blocky.service.impl.hibernate;

import java.util.List;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.model.impl.hibernate.cst.HibernateCategoryImpl;
import eu.ba30.re.blocky.service.CstManager;

@Service
public class HibernateCstManagerImpl implements CstManager {
    @PersistenceContext
    private EntityManager entityManager;

    @Nonnull
    @Transactional(readOnly = true)
    @Override
    public List<Category> getCategoryList() {
        final CriteriaQuery<HibernateCategoryImpl> query = entityManager.getCriteriaBuilder().createQuery(HibernateCategoryImpl.class);
        query.select(query.from(HibernateCategoryImpl.class));
        return Lists.newArrayList(Validate.validateResult(entityManager.createQuery(query).getResultList()));
    }

    @Nonnull
    @Transactional(readOnly = true)
    @Override
    public Category getCategoryById(int categoryId) {
        return Validate.validateResult(entityManager.find(HibernateCategoryImpl.class, categoryId));
    }
}
