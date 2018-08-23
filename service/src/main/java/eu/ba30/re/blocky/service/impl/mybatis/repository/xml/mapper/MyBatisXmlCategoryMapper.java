package eu.ba30.re.blocky.service.impl.mybatis.repository.xml.mapper;

import java.util.List;

import javax.annotation.Nullable;

import eu.ba30.re.blocky.model.cst.Category;

public interface MyBatisXmlCategoryMapper {
    @Nullable
    Category getCategoryById(int categoryId);

    @Nullable
    List<Category> getCategoryList();
}
