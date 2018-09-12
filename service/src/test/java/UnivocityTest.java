import java.util.List;

import org.apache.ibatis.io.Resources;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.univocity.parsers.annotations.Headers;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import eu.ba30.re.blocky.model.cst.Category;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class UnivocityTest {
    @Test
    public void test() throws Exception {
        final BeanListProcessor<UnivocityCategoryImpl> rowProcessor = new BeanListProcessor<>(UnivocityCategoryImpl.class);

        final CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.getFormat().setLineSeparator("\n");
        parserSettings.setHeaderExtractionEnabled(false);
        parserSettings.setProcessor(rowProcessor);

        final CsvParser parser = new CsvParser(parserSettings);
        parser.parse(Resources.getResourceAsReader("csv/test-data-cst-category.csv"));

        final List<UnivocityCategoryImpl> actual = rowProcessor.getBeans();

        final UnivocityCategoryImpl expected1 = new UnivocityCategoryImpl();
        expected1.setId(1);
        expected1.setName("CategoryName#1");
        expected1.setDescription("CategoryDescription#1");
        final UnivocityCategoryImpl expected2 = new UnivocityCategoryImpl();
        expected2.setId(2);
        expected2.setName("CategoryName#2");
        expected2.setDescription("CategoryDescription#2");

        assertReflectionEquals(Lists.newArrayList(expected1, expected2), actual);
    }

    /* Nastavit header sa da aj cez parserSettings.setHeader(...) */
    @Headers(sequence = { "ID", "NAME", "DESCR" })
    public static class UnivocityCategoryImpl extends Category {
        @Parsed(field = "ID")
        private Integer id;
        @Parsed(field = "NAME")
        private String name;
        @Parsed(field = "DESCR")
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
