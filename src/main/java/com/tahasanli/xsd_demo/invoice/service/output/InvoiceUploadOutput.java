package com.tahasanli.xsd_demo.invoice.service.output;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceUploadOutput {
    private InvoiceServiceStatus status;
    private String message;
}
