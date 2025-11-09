package com.tahasanli.xsd_demo.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Configuration
public class XdsValidationConfig {
    private final ResourceLoader resourceLoader;

    @Bean
    public Schema invoiceSchema() throws IOException, SAXException {
        Resource resource = resourceLoader.getResource("classpath:xsd/schemat.xsd");
        File xsdFile = resource.getFile();

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);
        Schema schema = factory.newSchema(xsdFile);
        return schema;
    }
}
