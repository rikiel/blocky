package eu.ba30.re.blocky.service.impl.db.impl.mapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import eu.ba30.re.blocky.model.cst.AttachmentType;

public class AttachmentTypeHandler extends BaseTypeHandler<AttachmentType> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AttachmentType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    @Override
    public AttachmentType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return AttachmentType.forId(rs.getInt(columnName));
    }

    @Override
    public AttachmentType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return AttachmentType.forId(rs.getInt(columnIndex));
    }

    @Override
    public AttachmentType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return AttachmentType.forId(cs.getInt(columnIndex));
    }
}
