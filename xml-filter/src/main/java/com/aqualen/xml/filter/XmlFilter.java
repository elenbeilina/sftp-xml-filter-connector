package com.aqualen.xml.filter;

import lombok.SneakyThrows;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.transforms.Transformation;
import org.apache.kafka.connect.transforms.util.SimpleConfig;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.util.Map;

import static org.apache.kafka.common.config.ConfigDef.Importance.HIGH;

public class XmlFilter<R extends ConnectRecord<R>> implements Transformation<R> {

    private static final String X_PATH_CONFIG = "x-path";
    private static final String X_PATH_DOC = "XPath value for filtering records.";
    private String xPathExpression;

    @SneakyThrows
    @Override
    public R apply(R r) {
        if (r.value() == null) {
            return r;
        }
        InputSource recordSource = new InputSource(new StringReader((String) r.value()));
        DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
        df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        DocumentBuilder builder = df.newDocumentBuilder();
        Document xmlDocument = builder.parse(recordSource);
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.compile(xPathExpression).evaluate(xmlDocument, XPathConstants.NODESET);

        if(nodeList.getLength() != 0){
            return r;
        }

        return null;
    }

    @Override
    public ConfigDef config() {
        return conf();
    }


    @Override
    public void configure(Map<String, ?> configs) {
        SimpleConfig simpleConfig = new SimpleConfig(this.config(), configs);

        this.xPathExpression = simpleConfig.getString(X_PATH_CONFIG);
    }

    @Override
    public void close() {
        //Nothing to close
    }

    /**
     * Method for generating configuration that is required for Xml filter.
     *
     * @return - configuration that manipulates Xml filter.
     */
    public static ConfigDef conf() {
        return new ConfigDef()
                .define(X_PATH_CONFIG,
                        ConfigDef.Type.STRING,
                        ConfigDef.NO_DEFAULT_VALUE,
                        HIGH,
                        X_PATH_DOC);
    }
}
