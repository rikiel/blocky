package eu.ba30.re.blocky.service.impl.db.impl.mapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nullable;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.impl.db.CstCategoryRepository;

@Service
public class InvoiceCategoryHandler extends BaseTypeHandler<Category> {
    @Autowired
    private CstCategoryRepository cstCategoryRepository;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Category parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    @Override
    public Category getNullableResult(ResultSet rs, String columnName) throws SQLException {
        final Integer id = rs.getObject(columnName, Integer.class);
        return getCategoryById(id);
    }

    @Override
    public Category getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        final Integer id = rs.getObject(columnIndex, Integer.class);
        return getCategoryById(id);
    }

    @Override
    public Category getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        final Integer id = cs.getObject(columnIndex, Integer.class);
        return getCategoryById(id);
    }

    @Nullable
    private Category getCategoryById(@Nullable final Integer id) {
        return id == null
                ? null
                : cstCategoryRepository.getById(id);
    }
}
