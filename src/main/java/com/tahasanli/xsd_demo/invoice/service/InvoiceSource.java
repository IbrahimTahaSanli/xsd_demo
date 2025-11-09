package com.tahasanli.xsd_demo.invoice.service;

import com.tahasanli.xsd_demo.invoice.service.dto.InvoiceDTO;

public interface InvoiceSource {
    boolean validateInvoice(String input);

    InvoiceDTO getInvoice(String input);
}
