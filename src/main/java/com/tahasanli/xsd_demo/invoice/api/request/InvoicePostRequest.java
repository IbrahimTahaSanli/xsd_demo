package com.tahasanli.xsd_demo.invoice.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoicePostRequest {
    @JsonProperty("base64xml")
    private String xmlBase64;
}
