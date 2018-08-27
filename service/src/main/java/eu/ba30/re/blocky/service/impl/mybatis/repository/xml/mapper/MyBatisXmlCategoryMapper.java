package eu.ba30.re.blocky.service.impl.mybatis.repository.xml.mapper;

import java.util.List;

import javax.annotation.Nullable;

import eu.ba30.re.blocky.model.impl.other.cst.CategoryImpl;

public interface MyBatisXmlCategoryMapper {
    @Nullable
    CategoryImpl getCategoryById(int categoryId);

    @Nullable
    List<CategoryImpl> getCategoryList();
}
