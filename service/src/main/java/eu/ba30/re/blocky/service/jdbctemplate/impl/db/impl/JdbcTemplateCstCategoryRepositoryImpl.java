package eu.ba30.re.blocky.service.jdbctemplate.impl.db.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.jdbctemplate.impl.db.JdbcTemplateCstCategoryRepository;

@Service
public class JdbcTemplateCstCategoryRepositoryImpl implements JdbcTemplateCstCategoryRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcTemplateCstCategoryRepositoryImpl.class);

    private static final String GET_ALL_CATEGORIES_SQL_REQUEST = ""
                                                                 + " SELECT * "
                                                                 + " FROM T_CST_CATEGORY ";
    private static final String GET_CATEGORY_BY_ID_SQL_REQUEST = ""
                                                                 + " SELECT * "
                                                                 + " FROM T_CST_CATEGORY "
                                                                 + " WHERE ID = ? ";

    @Autowired
    private JdbcTemplate jdbc;

    @Nonnull
    @Override
    public List<Category> getAllCategories() {
        return jdbc.query(GET_ALL_CATEGORIES_SQL_REQUEST,
                new CategoryMapper());
    }

    @Nonnull
    @Override
    public Category getById(int categoryId) {
        return jdbc.queryForObject(GET_CATEGORY_BY_ID_SQL_REQUEST,
                new Object[] { categoryId },
                new CategoryMapper());
    }

    private static class CategoryMapper implements RowMapper<Category> {
        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            final Category category = new Category();

            category.setId(rs.getInt("ID"));
            category.setDescription(rs.getString("DESCR"));
            category.setName(rs.getString("NAME"));

            log.debug("Loaded category: {}", category);
            return category;
        }
    }
}
