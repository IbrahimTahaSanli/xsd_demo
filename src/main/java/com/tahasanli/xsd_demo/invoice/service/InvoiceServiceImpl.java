package com.tahasanli.xsd_demo.invoice.service;

import com.tahasanli.xsd_demo.exception.XmlValidationException;
import com.tahasanli.xsd_demo.invoice.data.entity.InvoiceEntity;
import com.tahasanli.xsd_demo.invoice.data.repository.InvoiceRepository;
import com.tahasanli.xsd_demo.invoice.service.dto.InvoiceDTO;
import com.tahasanli.xsd_demo.invoice.service.input.InvoiceUploadInput;
import com.tahasanli.xsd_demo.invoice.service.output.InvoiceServiceStatus;
import com.tahasanli.xsd_demo.invoice.service.output.InvoiceUploadOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository repository;
    private final List<InvoiceSource> sources;

    @Override
    public InvoiceUploadOutput saveInvoice(InvoiceUploadInput input) {
        InvoiceSource source = sources.stream().filter(
                invoiceSource -> invoiceSource.validateInvoice(input.getXsdXml())
        ).findFirst().orElseThrow(() -> new XmlValidationException("Couldn't Verify Input Xml"));

        InvoiceDTO dto = source.getInvoice(input.getXsdXml());

        InvoiceEntity entity = InvoiceEntity.builder()
                .nip(dto.getNip())
                .p1(dto.getP1())
                .p2(dto.getP2())
                .build();

        repository.save(entity);

        return InvoiceUploadOutput.builder()
                .status(InvoiceServiceStatus.SUCCESS)
                .message("Invoice saved successfully")
                .build();
    }

}
