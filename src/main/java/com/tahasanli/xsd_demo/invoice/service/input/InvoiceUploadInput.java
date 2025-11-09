package com.tahasanli.xsd_demo.invoice.service.input;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceUploadInput {
    private String xsdXml;
}
