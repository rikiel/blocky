package eu.ba30.re.blocky.service.impl.mybatis.repository;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.impl.mybatis.repository.mapper.MyBatisCategoryMapper;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;

@Service
public class MyBatisCstCategoryRepositoryImpl implements CstCategoryRepository {
    @Autowired
    private MyBatisCategoryMapper categoryMapper;

    @Nonnull
    @Override
    public List<Category> getCategoryList() {
        return Validate.validateResult(categoryMapper.getCategoryList());
    }

    @Nonnull
    @Override
    public Category getCategoryById(int categoryId) {
        return Validate.validateResult(categoryMapper.getCategoryById(categoryId));
    }
}
