package eu.ba30.re.blocky.service.impl.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.impl.db.CstCategoryRepository;
import eu.ba30.re.blocky.service.impl.db.impl.mapper.CategoryMapper;
import eu.ba30.re.blocky.utils.Validate;

@Service
public class CstCategoryRepositoryImpl implements CstCategoryRepository {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Nonnull
    @Override
    public List<Category> getAllCategories() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
            return Validate.validateResult(categoryMapper.getAllCategories());
        }
    }

    @Nonnull
    @Override
    public Category getById(int categoryId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            final CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
            return Validate.validateResult(categoryMapper.getCategory(categoryId));
        }
    }
}
