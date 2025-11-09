package com.tahasanli.xsd_demo.invoice.service;

import com.tahasanli.xsd_demo.invoice.service.input.InvoiceUploadInput;
import com.tahasanli.xsd_demo.invoice.service.output.InvoiceUploadOutput;

public interface InvoiceService {
    InvoiceUploadOutput saveInvoice(InvoiceUploadInput input);
}
