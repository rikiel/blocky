import java.util.List;

import org.apache.ibatis.io.Resources;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import eu.ba30.re.blocky.model.cst.Category;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class OpenCsvTest {
    @Test
    public void test() throws Exception {
        final List<OpenCsvCategoryImpl> actual = new CsvToBeanBuilder<OpenCsvCategoryImpl>(Resources.getResourceAsReader("csv/test-data-cst-category.csv"))
                .withType(OpenCsvCategoryImpl.class)
                .withMappingStrategy(new MappingStrategy())
                .build()
                .parse();

        final OpenCsvCategoryImpl expected1 = new OpenCsvCategoryImpl();
        expected1.setId(1);
        expected1.setName("CategoryName#1");
        expected1.setDescription("CategoryDescription#1");
        final OpenCsvCategoryImpl expected2 = new OpenCsvCategoryImpl();
        expected2.setId(2);
        expected2.setName("CategoryName#2");
        expected2.setDescription("CategoryDescription#2");

        assertReflectionEquals(Lists.newArrayList(expected1, expected2), actual);
    }

    public static class OpenCsvCategoryImpl extends Category {
        @CsvBindByName(column = "ID", required = true)
        private Integer id;
        @CsvBindByName(column = "NAME", required = true)
        private String name;
        @CsvBindByName(column = "DESCR", required = true)
        private String description;

        @Override
        public Integer getId() {
            return id;
        }

        @Override
        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public void setDescription(String description) {
            this.description = description;
        }
    }

    private static class MappingStrategy extends HeaderColumnNameMappingStrategy<OpenCsvCategoryImpl> {
        MappingStrategy() {
            setType(OpenCsvCategoryImpl.class);
        }

        @Override
        public void captureHeader(CSVReader reader) {
            headerIndex.initializeHeaderIndex(new String[] { "ID", "NAME", "DESCR" });
        }
    }
}
