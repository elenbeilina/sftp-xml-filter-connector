package com.aqualen.xml.filter;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.sink.SinkRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class XmlFilterTest {

    private SinkRecord record;

    @BeforeEach
    void setUp() {
        final String recordValue = "<?xml version=\"1.0\"?>\n" +
                "<people>\n" +
                "    <name>Luke Skywalker</name>\n" +
                "    <height>172</height>\n" +
                "    <mass>77</mass>\n" +
                "    <hair_color>blond</hair_color>\n" +
                "    <skin_color>fair</skin_color>\n" +
                "    <eye_color>blue</eye_color>\n" +
                "    <birth_year>19BBY</birth_year>\n" +
                "    <gender>male</gender>\n" +
                "</people>\n";
        record = new SinkRecord("test", 1, Schema.STRING_SCHEMA, "test",
                Schema.STRING_SCHEMA, recordValue, 1);
    }


    @Test
    void checkFilter() {
        XmlFilter<SinkRecord> sinkRecordXmlFilter = new XmlFilter<>();
        sinkRecordXmlFilter.configure(new HashMap<String, String>() {{
            put("x-path", "/people/eye_color/text()");
            put("filter.list", "yellow, blue");
        }});
        SinkRecord result = sinkRecordXmlFilter.apply(record);

        assertNotNull(result);
    }

    @Test
    void checkThatFilterExcluded() {
        XmlFilter<SinkRecord> sinkRecordXmlFilter = new XmlFilter<>();
        sinkRecordXmlFilter.configure(new HashMap<String, String>() {{
            put("x-path", "/people/name/text()");
            put("filter.list", "C-3PO, R2-D2");
        }});
        SinkRecord result = sinkRecordXmlFilter.apply(record);

        assertNull(result);
    }

}