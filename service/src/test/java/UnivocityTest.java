import java.io.StringWriter;
import java.nio.file.Files;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.univocity.parsers.annotations.Headers;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import eu.ba30.re.blocky.model.cst.Category;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class UnivocityTest {
    @Test
    public void testReadCsv() throws Exception {
        final BeanListProcessor<UnivocityCategoryImpl> rowProcessor = new BeanListProcessor<>(UnivocityCategoryImpl.class);

        final CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        settings.setHeaderExtractionEnabled(false);
        settings.setProcessor(rowProcessor);

        final CsvParser parser = new CsvParser(settings);
        parser.parse(Resources.getResourceAsReader("csv/test-data-cst-category.csv"));

        final List<UnivocityCategoryImpl> actual = rowProcessor.getBeans();

        assertReflectionEquals(getExpected(), actual);
    }

    @Test
    public void testWriteCsv() throws Exception {
        final CsvWriterSettings settings = new CsvWriterSettings();
        settings.getFormat().setLineSeparator("\n");
        settings.setHeaderWritingEnabled(false);
        settings.setRowWriterProcessor(new BeanWriterProcessor<>(UnivocityCategoryImpl.class));

        final StringWriter output = new StringWriter();
        final CsvWriter writer = new CsvWriter(output, settings);
        writer.processRecordsAndClose(getExpected());

        final List<String> expectedLines = Files.readAllLines(Resources.getResourceAsFile("csv/test-data-cst-category.csv").toPath());
        final List<String> actualLines = Lists.newArrayList(output.getBuffer().toString().split("\n"));

        assertReflectionEquals(expectedLines, actualLines);
    }

    private List<UnivocityCategoryImpl> getExpected() {
        final UnivocityCategoryImpl expected1 = new UnivocityCategoryImpl();
        expected1.setId(1);
        expected1.setName("CategoryName#1");
        expected1.setDescription("CategoryDescription#1");
        final UnivocityCategoryImpl expected2 = new UnivocityCategoryImpl();
        expected2.setId(2);
        expected2.setName("CategoryName#2");
        expected2.setDescription("CategoryDescription#2");

        return Lists.newArrayList(expected1, expected2);
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
