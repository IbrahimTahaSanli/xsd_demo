package com.tahasanli.xsd_demo.invoice.api;

import com.tahasanli.xsd_demo.invoice.api.request.InvoicePostRequest;
import com.tahasanli.xsd_demo.invoice.api.response.InvoicePostSuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/invoices")
public interface InvoiceApi {
    @PostMapping
    ResponseEntity<InvoicePostSuccessResponse> postInvoice(@RequestBody InvoicePostRequest request);
}
