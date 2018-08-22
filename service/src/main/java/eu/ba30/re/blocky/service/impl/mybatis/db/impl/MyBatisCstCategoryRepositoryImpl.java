package eu.ba30.re.blocky.service.impl.mybatis.db.impl;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.impl.mybatis.db.impl.mapper.MyBatisCategoryMapper;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;

@Service
public class MyBatisCstCategoryRepositoryImpl implements CstCategoryRepository {
    @Autowired
    private MyBatisCategoryMapper categoryMapper;

    @Nonnull
    @Override
    public List<Category> getAllCategories() {
        return Validate.validateResult(categoryMapper.getAllCategories());
    }

    @Nonnull
    @Override
    public Category getById(int categoryId) {
        return Validate.validateResult(categoryMapper.getCategory(categoryId));
    }
}
