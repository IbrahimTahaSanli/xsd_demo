package com.tahasanli.xsd_demo.invoice.service;

import com.tahasanli.xsd_demo.exception.InternalErrorException;
import com.tahasanli.xsd_demo.exception.XmlValidationException;
import com.tahasanli.xsd_demo.generated.Faktura;
import com.tahasanli.xsd_demo.generated.TPodmiot1;
import com.tahasanli.xsd_demo.invoice.service.dto.InvoiceDTO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FakturaServiceImplTest {
    @Mock
    private Schema invoiceSchema;

    @Mock
    private Validator validator;

    @Mock
    private JAXBContext mockContext;

    @Mock
    private Unmarshaller mockUnmarshaller;

    @InjectMocks
    private FakturaService service;

    @Test
    @SneakyThrows
    public void whenValidateInvoiceWithRight_returnTrue() {
        when(invoiceSchema.newValidator()).thenReturn(validator);

        assertTrue(service.validateInvoice(""));

        verify(invoiceSchema, times(1)).newValidator();
        verify(validator, times(1)).validate(any(Source.class));
    }

    @Test
    @SneakyThrows
    public void whenValidateInvoiceThrowsIOException_ThrowException() {
        when(invoiceSchema.newValidator()).thenReturn(validator);
        doThrow(new IOException()).when(validator).validate(any(Source.class));

        assertThrows(InternalErrorException.class, () -> service.validateInvoice(""));

        verify(invoiceSchema, times(1)).newValidator();
        verify(validator, times(1)).validate(any(Source.class));
    }

    @Test
    @SneakyThrows
    public void whenValidateInvoiceThrowsValidationException_ThrowException() {
        when(invoiceSchema.newValidator()).thenReturn(validator);
        doThrow(new SAXException()).when(validator).validate(any(Source.class));

        assertThrows(XmlValidationException.class, () -> service.validateInvoice(""));

        verify(invoiceSchema, times(1)).newValidator();
        verify(validator, times(1)).validate(any(Source.class));
    }

    @Test
    @SneakyThrows
    public void whenGetInvoiceWithRightInput_ReturnSuccess() {

        Faktura mockFaktura = new Faktura();
        Faktura.Podmiot1 pod1 = new Faktura.Podmiot1();
        TPodmiot1 tPodmiot1 = new TPodmiot1();
        tPodmiot1.setNIP("2133");
        pod1.setDaneIdentyfikacyjne(tPodmiot1);
        mockFaktura.setPodmiot1(pod1);

        Faktura.Fa fa = new Faktura.Fa();
        fa.setP1(DatatypeFactory.newInstance().newXMLGregorianCalendar(String.valueOf(LocalDate.now())));
        fa.setP2("asdasd");
        mockFaktura.setFa(fa);

        try (MockedStatic<JAXBContext> staticMock = mockStatic(JAXBContext.class)) {
            staticMock.when(() -> JAXBContext.newInstance(Faktura.class)).thenReturn(mockContext);

            when(mockContext.createUnmarshaller()).thenReturn(mockUnmarshaller);
            when(mockUnmarshaller.unmarshal(any(StringReader.class)))
                    .thenReturn(mockFaktura);

            InvoiceDTO dto = service.getInvoice("");

            assertNotNull(dto);
            assertEquals("asdasd", dto.getP2());
        }
    }

    @Test
    @SneakyThrows
    public void whenGetInvoiceThrowException_ThrowException() {

        Faktura mockFaktura = new Faktura();
        Faktura.Podmiot1 pod1 = new Faktura.Podmiot1();
        TPodmiot1 tPodmiot1 = new TPodmiot1();
        tPodmiot1.setNIP("2133");
        pod1.setDaneIdentyfikacyjne(tPodmiot1);
        mockFaktura.setPodmiot1(pod1);

        Faktura.Fa fa = new Faktura.Fa();
        fa.setP1(DatatypeFactory.newInstance().newXMLGregorianCalendar(String.valueOf(LocalDate.now())));
        fa.setP2("asdasd");
        mockFaktura.setFa(fa);

        try (MockedStatic<JAXBContext> staticMock = mockStatic(JAXBContext.class)) {
            staticMock.when(() -> JAXBContext.newInstance(Faktura.class)).thenReturn(mockContext);

            when(mockContext.createUnmarshaller()).thenReturn(mockUnmarshaller);
            when(mockUnmarshaller.unmarshal(any(StringReader.class)))
                    .thenThrow(new JAXBException(""));

            assertThrows(InternalErrorException.class, () -> service.getInvoice(""));
        }
    }
}
