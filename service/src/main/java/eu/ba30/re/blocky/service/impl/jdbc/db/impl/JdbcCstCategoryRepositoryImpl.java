package eu.ba30.re.blocky.service.impl.jdbc.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import eu.ba30.re.blocky.common.exception.DatabaseException;
import eu.ba30.re.blocky.common.utils.Validate;
import eu.ba30.re.blocky.model.cst.Category;
import eu.ba30.re.blocky.service.impl.jdbc.db.JdbcCstCategoryRepository;

@Service
public class JdbcCstCategoryRepositoryImpl implements JdbcCstCategoryRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcCstCategoryRepositoryImpl.class);

    private static final String GET_ALL_CATEGORIES_SQL_REQUEST = ""
                                                                 + " SELECT * "
                                                                 + " FROM T_CST_CATEGORY ";
    private static final String GET_CATEGORY_BY_ID_SQL_REQUEST = ""
                                                                 + " SELECT * "
                                                                 + " FROM T_CST_CATEGORY "
                                                                 + " WHERE ID = ? ";

    private final CategoryMapper MAPPER = new CategoryMapper();

    @Autowired
    private Connection connection;

    @Nonnull
    @Override
    public List<Category> getAllCategories() {
        final List<Category> results = Lists.newArrayList();
        try (final Statement statement = connection.createStatement()) {
            try (final ResultSet resultSet = statement.executeQuery(GET_ALL_CATEGORIES_SQL_REQUEST)) {
                while (resultSet.next()) {
                    results.add(MAPPER.mapRow(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("SqlException was thrown", e);
        }
        return results;
    }

    @Nonnull
    @Override
    public Category getById(int categoryId) {
        try (final PreparedStatement statement = connection.prepareStatement(GET_CATEGORY_BY_ID_SQL_REQUEST)) {
            statement.setInt(1, categoryId);
            try (final ResultSet resultSet = statement.executeQuery()) {
                Category category = null;
                while (resultSet.next()) {
                    Validate.isNull(category, "More Categories were returned!");

                    category = MAPPER.mapRow(resultSet);
                }
                Validate.notNull(category, "Category was not found for id=" + categoryId);
                return category;
            }
        } catch (SQLException e) {
            throw new DatabaseException("SqlException was thrown", e);
        }
    }

    private static class CategoryMapper {
        @Nonnull
        Category mapRow(ResultSet rs) throws SQLException {
            final Category category = new Category();

            category.setId(rs.getInt("ID"));
            category.setDescription(rs.getString("DESCR"));
            category.setName(rs.getString("NAME"));

            log.debug("Loaded category: {}", category);
            return category;
        }
    }
}
