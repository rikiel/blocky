import java.io.StringWriter;
import java.nio.file.Files;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import eu.ba30.re.blocky.model.cst.Category;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class OpenCsvTest {
    @Test
    public void testReadCsv() throws Exception {
        final List<OpenCsvCategoryImpl> actual = new CsvToBeanBuilder<OpenCsvCategoryImpl>(Resources.getResourceAsReader("csv/test-data-cst-category.csv"))
                .withType(OpenCsvCategoryImpl.class)
                .build()
                .parse();

        assertReflectionEquals(getExpected(), actual);
    }

    @Test
    public void testWriteCsv() throws Exception {
        final StringWriter output = new StringWriter();
        new StatefulBeanToCsvBuilder<OpenCsvCategoryImpl>(output)
                .withApplyQuotesToAll(false)
                .build()
                .write(getExpected());

        final List<String> expectedLines = Files.readAllLines(Resources.getResourceAsFile("csv/test-data-cst-category.csv").toPath());
        final List<String> actualLines = Lists.newArrayList(output.getBuffer().toString().split("\n"));

        assertReflectionEquals(expectedLines, actualLines);
    }

    private List<OpenCsvCategoryImpl> getExpected() {
        final OpenCsvCategoryImpl expected1 = new OpenCsvCategoryImpl();
        expected1.setId(1);
        expected1.setName("CategoryName#1");
        expected1.setDescription("CategoryDescription#1");
        final OpenCsvCategoryImpl expected2 = new OpenCsvCategoryImpl();
        expected2.setId(2);
        expected2.setName("CategoryName#2");
        expected2.setDescription("CategoryDescription#2");

        return Lists.newArrayList(expected1, expected2);
    }

    public static class OpenCsvCategoryImpl extends Category {
        @CsvBindByPosition(position = 0, required = true)
        private Integer id;
        @CsvBindByPosition(position = 1, required = true)
        private String name;
        @CsvBindByPosition(position = 2, required = true)
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
}
