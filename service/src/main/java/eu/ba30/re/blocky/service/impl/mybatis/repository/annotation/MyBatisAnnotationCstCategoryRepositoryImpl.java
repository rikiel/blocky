package eu.ba30.re.blocky.service.impl.mybatis.repository.annotation;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.impl.mybatis.repository.annotation.mapper.MyBatisAnnotationCategoryMapper;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;

@Service
public class MyBatisAnnotationCstCategoryRepositoryImpl implements CstCategoryRepository {
    @Autowired
    private MyBatisAnnotationCategoryMapper categoryMapper;

    @Nonnull
    @Override
    public List<Category> getCategoryList() {
        return Lists.newArrayList(Validate.validateResult(categoryMapper.getCategoryList()));
    }

    @Nonnull
    @Override
    public Category getCategoryById(int categoryId) {
        return Validate.validateResult(categoryMapper.getCategoryById(categoryId));
    }
}
