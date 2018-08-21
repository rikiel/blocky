package eu.ba30.re.blocky.service.impl.jdbctemplate;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.CstManager;
import eu.ba30.re.blocky.service.impl.jdbctemplate.db.JdbcTemplateCstCategoryRepository;

@Service
@Primary
public class JdbcTemplateCstManagerImpl implements CstManager {
    @Autowired
    private JdbcTemplateCstCategoryRepository cstCategoryRepository;

    @Nonnull
    @Transactional(readOnly = true)
    @Override
    public List<Category> getCategories() {
        return cstCategoryRepository.getAllCategories();
    }

    @Nonnull
    @Transactional(readOnly = true)
    @Override
    public Category getCategory(int categoryId) {
        return cstCategoryRepository.getById(categoryId);
    }
}
