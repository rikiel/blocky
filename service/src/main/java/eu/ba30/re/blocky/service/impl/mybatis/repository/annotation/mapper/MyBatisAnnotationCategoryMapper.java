package eu.ba30.re.blocky.service.impl.mybatis.repository.annotation.mapper;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import eu.ba30.re.blocky.model.cst.Category;

public interface MyBatisAnnotationCategoryMapper {
    @Nullable
    @Results(id = "getCategoryById", value = {
            @Result(property = "id", column = "ID", id = true),
            @Result(property = "name", column = "NAME"),
            @Result(property = "description", column = "DESCR")
    })
    @Select({
            "SELECT *",
            "FROM T_CST_CATEGORY",
            "WHERE ID = #{categoryId}",
    })
    Category getCategoryById(@Param("categoryId") int categoryId);

    @Nullable
    @Results(id = "getCategoryList", value = {
            @Result(property = "id", column = "ID", id = true),
            @Result(property = "name", column = "NAME"),
            @Result(property = "description", column = "DESCR")
    })
    @Select({
            "SELECT *",
            "FROM T_CST_CATEGORY",
    })
    List<Category> getCategoryList();
}