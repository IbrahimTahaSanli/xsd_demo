package com.tahasanli.xsd_demo.invoice.service;

import com.tahasanli.xsd_demo.invoice.data.entity.InvoiceEntity;
import com.tahasanli.xsd_demo.invoice.data.repository.InvoiceRepository;
import com.tahasanli.xsd_demo.invoice.service.dto.InvoiceDTO;
import com.tahasanli.xsd_demo.invoice.service.input.InvoiceUploadInput;
import com.tahasanli.xsd_demo.invoice.service.output.InvoiceUploadOutput;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceImplTest {
    @Mock
    InvoiceSource invoiceSource;

    @Mock
    InvoiceRepository repository;

    InvoiceServiceImpl invoiceService;

    @BeforeEach
    public void setupForEach() {
        invoiceService = new InvoiceServiceImpl(repository, List.of(invoiceSource));
    }

    @Test
    @SneakyThrows
    public void whenInvoiceIsValid_returnSuccess() {
        when(invoiceSource.validateInvoice(anyString())).thenReturn(true);
        InvoiceDTO invoiceDTO = InvoiceDTO.builder()
                .nip("nip")
                .p1("p1")
                .p2("p2")
                .build();
        when(invoiceSource.getInvoice(anyString())).thenReturn(invoiceDTO);
        when(repository.save(any(InvoiceEntity.class))).thenReturn(new InvoiceEntity());

        InvoiceUploadInput input = InvoiceUploadInput.builder()
                .xsdXml("asdasdasdasd")
                .build();
        InvoiceUploadOutput output = invoiceService.saveInvoice(input);

        verify(invoiceSource, times(1)).validateInvoice(anyString());
        verify(invoiceSource, times(1)).getInvoice(anyString());
        verify(repository, times(1)).save(any(InvoiceEntity.class));


    }
}
