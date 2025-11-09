package com.tahasanli.xsd_demo.invoice.service;

import com.tahasanli.xsd_demo.exception.InternalErrorException;
import com.tahasanli.xsd_demo.exception.XmlValidationException;
import com.tahasanli.xsd_demo.generated.Faktura;
import com.tahasanli.xsd_demo.invoice.service.dto.InvoiceDTO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;

@Service
@RequiredArgsConstructor
public class FakturaService implements InvoiceSource {
    private final Schema invoiceSchema;


    @Override
    public boolean validateInvoice(String input) {
        Validator validator = invoiceSchema.newValidator();

        StringReader reader = new StringReader(input);
        StreamSource source = new StreamSource(reader);

        try {
            validator.validate(source);
        } catch (IOException e) {
            throw new InternalErrorException(e.getMessage());
        } catch (SAXException e) {
            throw new XmlValidationException(e.getMessage());
        }

        return true;
    }

    @Override
    public InvoiceDTO getInvoice(String input) {
        Faktura faktura;
        try {
            JAXBContext context = JAXBContext.newInstance(Faktura.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            faktura = (Faktura) unmarshaller.unmarshal(new StringReader(input));
        } catch (JAXBException e) {
            throw new InternalErrorException(e.getMessage());
        }

        InvoiceDTO invoiceSaveInput = InvoiceDTO.builder()
                .nip(faktura.getPodmiot1().getDaneIdentyfikacyjne().getNIP())
                .p1(faktura.getFa().getP1().toString())
                .p2(faktura.getFa().getP2())
                .build();

        return invoiceSaveInput;
    }

}
