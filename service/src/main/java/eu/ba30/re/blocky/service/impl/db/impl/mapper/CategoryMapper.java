package eu.ba30.re.blocky.service.impl.db.impl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import eu.ba30.re.blocky.model.cst.Category;

public interface CategoryMapper {
    @Results(id = "getCategory", value = {
            @Result(property = "id", column = "ID", id = true),
            @Result(property = "name", column = "NAME"),
            @Result(property = "description", column = "DESCR")
    })
    @Select(""
            + " SELECT * "
            + " FROM T_CST_CATEGORY "
            + " WHERE ID = #{id} "
    )
    Category getCategory(int id);

    @Results(id = "getAllCategories", value = {
            @Result(property = "id", column = "ID", id = true),
            @Result(property = "name", column = "NAME"),
            @Result(property = "description", column = "DESCR")
    })
    @Select(""
            + " SELECT * "
            + " FROM T_CST_CATEGORY "
    )
    List<Category> getAllCategories();
}
