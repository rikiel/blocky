package eu.ba30.re.blocky.service.impl.mybatis.repository.xml;

import java.util.List;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.impl.mybatis.repository.xml.mapper.MyBatisXmlCategoryMapper;
import eu.ba30.re.blocky.service.impl.repository.CstCategoryRepository;

@Service
public class MyBatisXmlCstCategoryRepositoryImpl implements CstCategoryRepository {
    @Autowired
    private MyBatisXmlCategoryMapper categoryMapper;

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
