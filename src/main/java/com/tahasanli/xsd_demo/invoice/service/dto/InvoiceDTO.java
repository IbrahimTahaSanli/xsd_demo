package com.tahasanli.xsd_demo.invoice.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDTO {
    private String nip;
    private String p1;
    private String p2;
}
